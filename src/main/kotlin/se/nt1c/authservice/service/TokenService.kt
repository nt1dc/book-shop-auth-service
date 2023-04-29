package se.nt1c.authservice.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import se.nt1c.authservice.dto.TokenValidationRequest


interface TokenService {
    fun validate(
        tokenValidationRequest: TokenValidationRequest,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse
    )
    fun extractToken(httpServletRequest: HttpServletRequest): String
}
