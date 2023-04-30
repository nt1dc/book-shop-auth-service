package se.nt1dc.authservice.dto

data class TokenValidationRequest(
    var roles: MutableList<String>
)
