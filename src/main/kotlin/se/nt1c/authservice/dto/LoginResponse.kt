package se.nt1c.authservice.dto

data class LoginResponse(
    var jwt: String,
    var login: String
)
