package se.nt1c.authservice.service

import jakarta.servlet.http.HttpServletRequest
import se.nt1c.authservice.dto.TokenValidationRequest


interface TokenService {
    fun validate(tokenValidationRequest: TokenValidationRequest, httpServletRequest: HttpServletRequest)
    fun extractToken(httpServletRequest: HttpServletRequest): String
}
