package se.nt1c.authservice.dto

data class TokenValidationRequest(
    var roles: MutableList<String>
)
