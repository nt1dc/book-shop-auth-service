package se.nt1c.authservice.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import se.nt1c.authservice.dto.TokenValidationRequest
import se.nt1c.authservice.service.TokenService

@RestController
@RequestMapping("/token")
class TokenController(
    val tokenService: TokenService, private val httpServletResponse: HttpServletResponse
) {
    @PostMapping("/validate")
    fun validate(
        @RequestBody tokenValidationRequest: TokenValidationRequest,
        @RequestHeader(name = "Authorization") jwt: String,
        httpServletResponse: HttpServletResponse
    ) {
        println("validate $tokenValidationRequest")
        tokenService.validate(tokenValidationRequest, jwt, httpServletResponse)
    }

    @GetMapping("/getName")
    fun getNameFromToken(httpServletRequest: HttpServletRequest): String {
        return tokenService.extractToken(httpServletRequest)
    }

}