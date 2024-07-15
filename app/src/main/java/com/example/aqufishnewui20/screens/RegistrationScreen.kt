package com.example.aqufishnewui20.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aqufishnewui20.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.regex.Pattern

data class UserInfo(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

@Preview
@Composable
fun RegisterScreen() {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var userInfo by remember { mutableStateOf(UserInfo("", "", "", "")) }
    var message by remember { mutableStateOf("") }

    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.finallogo),
            contentDescription = "Login Page",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = userInfo.name,
            onValueChange = { userInfo = userInfo.copy(name = it) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = userInfo.email,
            onValueChange = { userInfo = userInfo.copy(email = it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = userInfo.password,
            onValueChange = { userInfo = userInfo.copy(password = it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = userInfo.confirmPassword,
            onValueChange = { userInfo = userInfo.copy(confirmPassword = it) },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (userInfo.password == userInfo.confirmPassword) {
                if (emailPattern.matcher(userInfo.email).matches()) {
                    auth.createUserWithEmailAndPassword(userInfo.email, userInfo.password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                auth.currentUser?.let { user ->
                                    val userId = user.uid
                                    val userData = hashMapOf(
                                        "name" to userInfo.name,
                                        "email" to userInfo.email,
                                        "uid" to userId
                                    )
                                    db.collection("users").document(userId)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            message = "Registration Successful!"
                                        }
                                        .addOnFailureListener { e ->
                                            message = "Error adding user: ${e.message}"
                                        }
                                } ?: run {
                                    message = "Error: User not authenticated."
                                }
                            } else {
                                message = "Registration Failed: ${task.exception?.message}"
                            }
                        }
                } else {
                    message = "Email address is badly formatted"
                }
            } else {
                message = "Passwords do not match"
            }
        }) {
            Text("Register")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message)
    }
}