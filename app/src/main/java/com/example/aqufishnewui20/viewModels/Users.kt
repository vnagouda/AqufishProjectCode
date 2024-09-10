package com.example.aqufishnewui20.viewModels

data class Users(
    val name: String = "",
    val email: String = "",
    val feederIDS: List<String> = emptyList() // Default to empty list if not present
)
