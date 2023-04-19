package se.nt1c.authservice.dto

import se.nt1c.authservice.entity.RoleEnum

data class TokenValidationRequest(
    var roles: MutableList<String>
)
