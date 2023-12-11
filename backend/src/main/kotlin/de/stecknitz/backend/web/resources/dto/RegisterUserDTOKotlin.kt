package de.stecknitz.backend.web.resources.dto

data class RegisterUserDTOKotlin(
    val id: Long,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
)
