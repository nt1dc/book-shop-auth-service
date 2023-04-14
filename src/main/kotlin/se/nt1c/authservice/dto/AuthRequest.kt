package se.nt1c.authservice.dto

data class AuthRequest(
    var login: String,
    var password: String
)
