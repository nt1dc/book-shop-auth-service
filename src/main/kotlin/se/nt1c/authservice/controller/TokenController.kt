package se.nt1c.authservice.controller

import org.springframework.web.bind.annotation.RestController
import se.nt1c.authservice.service.TokenService

@RestController
class TokenController(val tokenService: TokenService) {
}