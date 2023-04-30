package se.nt1dc.authservice.service

import jakarta.servlet.http.HttpServletResponse
import se.nt1dc.authservice.dto.RequiredAuthorities


interface TokenService {
    fun validate(
        requiredAuthorities: RequiredAuthorities,
        authorizationHeader: String,
        httpServletResponse: HttpServletResponse
    )
}
