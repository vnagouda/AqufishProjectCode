package com.example.aqufishnewui20.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class LoginViewModel: ViewModel() {

    private val TAG = "LoginViewModel"

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

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

    private fun fetchUserFeeders(userId: String) {
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    val feederIds = document.get("feederIds") as List<String>
                    fetchFeederDetails(feederIds)
                }
            }
    }

    private fun fetchFeederDetails(feederIds: List<String>) {
        val feedersList = mutableListOf<Map<String, Any>>()
        for (feederId in feederIds) {
            db.collection("feeders").document(feederId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        document.data?.let { feedersList.add(it) }
                        _userFeeders.value = feedersList
                    }
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
                    _loginSuccess.value = true
                    fetchUserFeeders(user.uid) // Fetch user details using user ID
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