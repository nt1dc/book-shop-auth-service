package se.nt1dc.authservice.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import se.nt1dc.authservice.dto.TokenValidationRequest


interface TokenService {
    fun validate(
        tokenValidationRequest: se.nt1dc.authservice.dto.TokenValidationRequest,
        authorizationHeader: String,
        httpServletResponse: HttpServletResponse
    )
}
