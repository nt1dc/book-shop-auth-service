package se.nt1c.authservice.dto

data class RegisterRequest(
    var login: String,
    var password: String
)