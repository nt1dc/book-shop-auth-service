package se.nt1dc.authservice.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import se.nt1dc.authservice.dto.AuthRequest
import se.nt1dc.authservice.dto.LoginResponse
import se.nt1dc.authservice.dto.RegisterRequest
import se.nt1dc.authservice.service.UserService


@RestController
class UserController(val userService: UserService) {
    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest) {
        userService.register(registerRequest)
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequest: AuthRequest): LoginResponse {
        return userService.login(authRequest)
    }

}