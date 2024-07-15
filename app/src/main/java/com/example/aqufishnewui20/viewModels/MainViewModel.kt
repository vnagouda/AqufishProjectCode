package com.example.aqufishnewui20.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel : ViewModel() {

    private val TAG = "LoginViewModel"

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    //private val name = MutableStateFlow("")

    val state = mutableStateOf("")
    private val _uid = MutableLiveData<String>()
    val uid: LiveData<String> get() = _uid

    // LiveData to observe user data
    private val _userData = MutableLiveData<Map<String, Any>?>()
    val userData: MutableLiveData<Map<String, Any>?> get() = _userData

    // LiveData to observe feeders
    private val _userFeeders = MutableLiveData<List<Map<String, Any>>>()
    val userFeeders: LiveData<List<Map<String, Any>>> get() = _userFeeders

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> get() = _loginSuccess

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }


    private val _authState = MutableLiveData<Boolean>()
    val authState: LiveData<Boolean> get() = _authState




    fun login() {
        _isLoading.value = true
        viewModelScope.launch {
            try {

                Log.d(TAG, "Attempting login with email: ${_email.value}")

                auth.signInWithEmailAndPassword(_email.value, _password.value).await()

                _loginSuccess.value = true


                val user = auth.currentUser
                if (user != null) {
                    _loginSuccess.value = true

                     // Fetch user details using user ID
                } else {
                    _loginSuccess.value = false
                }
                // Handle successful login
            } catch (e: Exception) {
                _loginSuccess.value = false
                Log.e(TAG, "Login failed", e)
                e.printStackTrace()
                // Handle login failure
            } finally {
                _isLoading.value = false
            }
        }
    }
}
