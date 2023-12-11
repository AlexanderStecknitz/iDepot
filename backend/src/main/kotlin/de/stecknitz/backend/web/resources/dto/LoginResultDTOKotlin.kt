package de.stecknitz.backend.web.resources.dto

data class LoginResultDTOKotlin(
    val token: String,
    val email: String,
    val depotId: List<Long>
)
