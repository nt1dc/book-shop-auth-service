package se.nt1dc.authservice.dto

data class AuthRequest(
    var login: String,
    var password: String
)
