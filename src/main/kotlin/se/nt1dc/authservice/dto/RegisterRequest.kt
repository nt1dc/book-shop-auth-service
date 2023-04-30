package se.nt1dc.authservice.dto

data class RegisterRequest(
    var login: String,
    var password: String
)