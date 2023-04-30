package se.nt1dc.authservice.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import se.nt1dc.authservice.dto.RequiredAuthorities
import se.nt1dc.authservice.service.TokenService

@RestController
@RequestMapping("/token")
class TokenController(
    val tokenService: TokenService
) {
    @PostMapping("/validate")
    fun validate(
        @RequestBody requiredAuthorities: RequiredAuthorities,
        @RequestHeader(name = "Authorization") authorizationHeader: String,
        httpServletResponse: HttpServletResponse
    ) {
        tokenService.validate(requiredAuthorities, authorizationHeader, httpServletResponse)
    }
}