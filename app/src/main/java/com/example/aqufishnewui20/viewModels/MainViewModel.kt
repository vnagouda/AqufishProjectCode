package com.example.aqufishnewui20.viewModels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.aqufishnewui20.screens.Alarm
import com.example.aqufishnewui20.screens.AlarmDao
import com.example.aqufishnewui20.screens.AppDatabase
import com.example.aqufishnewui20.workers.MotorWorker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.util.UUID
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(
    private val alarmDao: AlarmDao,
    private val workManager: WorkManager,
    private val context: Context
) : ViewModel() {

    private val TAG = "MainViewModel"

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage = Firebase.storage
    private val videoRef = storage.reference.child("videos")

    private val _videoUrl = MutableStateFlow<String?>(null)
    val videoUrl: StateFlow<String?> get() = _videoUrl


    private val _isAlarmSet = MutableStateFlow(false)
    val isAlarmSet: StateFlow<Boolean> = _isAlarmSet



    private val _statusMessage = MutableStateFlow<String>("")
    val statusMessage: StateFlow<String> = _statusMessage

    private val _uid = MutableLiveData<String>()
    val uid: LiveData<String> = _uid

    private val _userData = MutableLiveData<Users?>()
    val userData: LiveData<Users?> = _userData

    private val _userFeeders = MutableLiveData<List<Feeder>>()
    val userFeeders: LiveData<List<Feeder>> = _userFeeders

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isUserSignedIn = MutableStateFlow(false)
    val isUserSignedIn: StateFlow<Boolean> = _isUserSignedIn

    init {
        checkUserAuthState()
    }

    fun checkUserAuthState() {
        viewModelScope.launch {
            _isUserSignedIn.value = auth.currentUser != null
        }
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    //private val database = AppDatabase.getDatabase(application)
    //private val workManager = WorkManager.getInstance(context)
    var selectedHour by mutableStateOf(0)
    var selectedMinute by mutableStateOf(0)
    private val _alarmTimes = alarmDao.getAllAlarms()
        .map { alarms ->
            alarms.map { alarm -> Pair(Pair(alarm.hour, alarm.minute), alarm.id) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val alarmTimes: StateFlow<List<Pair<Pair<Int, Int>, UUID>>> get() = _alarmTimes

    private val _isAlarmTriggered = MutableStateFlow(false)
    val isAlarmTriggered: StateFlow<Boolean> = _isAlarmTriggered


    @RequiresApi(Build.VERSION_CODES.O)
    fun addAlarm(context: Context) {
        val now = LocalDateTime.now()
        val alarmTime = now.withHour(selectedHour).withMinute(selectedMinute).withSecond(0)

        // If alarm time is before now, schedule it for the next day
        val nextAlarmTime = if (alarmTime.isBefore(now)) {
            alarmTime.plusDays(1)
        } else {
            alarmTime
        }

        val workRequest = scheduleAlarm(context ,nextAlarmTime)
        val newAlarm = Alarm(
            id = workRequest.id,
            hour = selectedHour,
            minute = selectedMinute,
            timestamp = nextAlarmTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
        )

        viewModelScope.launch {
            alarmDao.insertAlarm(newAlarm)
            Log.d("AlarmViewModel", "Alarm added: $newAlarm")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleAlarm(context: Context, alarmTime: LocalDateTime): OneTimeWorkRequest {
        val url = "http://10.17.220.94/motor/speed?value=120"
        val urlOff = "http://10.17.220.94/motor/off"

        val now = LocalDateTime.now()
        val delay = if (alarmTime.isAfter(now)) {
            java.time.Duration.between(now, alarmTime).toMillis()
        } else {
            java.time.Duration.between(now, alarmTime.plusDays(1)).toMillis()
        }

        val dataOn = Data.Builder()
            .putString("url", url)
            .build()

        val workRequestOn = OneTimeWorkRequest.Builder(MotorWorker::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(dataOn)
            .build()

        Log.d("AlarmViewModel", "Scheduling alarm for $alarmTime with delay $delay ms")

        workManager.enqueue(workRequestOn)

        // Observe the status of the ON WorkRequest
        workManager.getWorkInfoByIdLiveData(workRequestOn.id).observeForever { workInfo ->
            if (workInfo != null && workInfo.state.isFinished) {
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    _isAlarmTriggered.value = true

                    // Schedule motor OFF after 10 seconds
                    val dataOff = Data.Builder()
                        .putString("url", urlOff)
                        .build()

                    val workRequestOff = OneTimeWorkRequest.Builder(MotorWorker::class.java)
                        .setInitialDelay(10, TimeUnit.SECONDS)
                        .setInputData(dataOff)
                        .build()

                    workManager.enqueue(workRequestOff)

                    // Reset the state after 10 seconds
                    viewModelScope.launch {
                        delay(10000)
                        _isAlarmTriggered.value = false
                    }
                }
            }
        }

        return workRequestOn
    }

    fun deleteAlarm(hour: Int, minute: Int) {
        viewModelScope.launch {
            val alarmToDelete = _alarmTimes.value.find { it.first.first == hour && it.first.second == minute }

            alarmToDelete?.let { (timePair, workId) ->
                // Cancel the scheduled work
                workManager.cancelWorkById(workId)

                // Remove the alarm from the database
                alarmDao.deleteAlarmById(workId)
            }
        }
    }



    fun login() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                Log.d(TAG, "Attempting login with email: ${_email.value}")

                auth.signInWithEmailAndPassword(_email.value, _password.value).await()

                _loginSuccess.value = true

                val user = auth.currentUser
                if (user != null) {
                    Log.e(TAG, "Login Successful")
                    _uid.value = user.uid
                    _loginSuccess.value = true
                    fetchUserData(user.uid)
                    //fetchUserFeeders(user.uid)
                } else {
                    _loginSuccess.value = false
                }

            } catch (e: Exception) {
                Log.e(TAG, "Login failed", e)
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        auth.signOut()
        _isUserSignedIn.value = false
    }

    fun fetchUserData(uid: String) {
        // Clear previous data
        _userFeeders.value = emptyList()

        viewModelScope.launch {
            try {
                val userDocument = db.collection("users").document(uid).get().await()
                val userData = userDocument.toObject(Users::class.java)
                _userData.value = userData

                val feederIds = userData?.feederIDS ?: emptyList()
                Log.d(TAG, "Feeder IDs fetched: $feederIds")

                feederIds.forEach { feederId ->
                    fetchUserFeeders(feederId) // Ensure this is called once per feeder ID
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching user data", e)
            }
        }
    }


    private fun fetchUserFeeders(feederId: String) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Fetching feeder data for ID: $feederId")
                val feederDocument = db.collection("feeders").document(feederId).get().await()

                // Log fetched feeder document data
                Log.d(TAG, "Feeder document fetched: ${feederDocument.data}")

                // Convert the document to a Feeder object
                val feeder = feederDocument.toObject(Feeder::class.java)

                // Log feeder data
                Log.d(TAG, "Successfully fetched feeder: $feeder")

                // Update the _userFeeders state
                val currentFeeders = _userFeeders.value.orEmpty()
                _userFeeders.value = currentFeeders + listOfNotNull(feeder)
                // Check and update video URL if IP address is present
                val ipAddress = feeder?.ipAddress
                if (ipAddress != null) {
                    val newVideoUrl = "rtsp://$ipAddress/mystream"
                    _videoUrl.value = newVideoUrl
                    Log.d(TAG, "Video URL updated to: $newVideoUrl")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error fetching feeder data for ID: $feederId", e)
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}

class MainViewModelFactory(
    private val alarmDao: AlarmDao,
    private val workManager: WorkManager,
    private val context: Context
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(alarmDao, workManager, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
