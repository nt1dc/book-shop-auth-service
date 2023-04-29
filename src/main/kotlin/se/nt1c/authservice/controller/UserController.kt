package se.nt1c.authservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import se.nt1c.authservice.dto.AuthRequest
import se.nt1c.authservice.dto.LoginResponse
import se.nt1c.authservice.dto.RegisterRequest
import se.nt1c.authservice.entity.Account
import se.nt1c.authservice.service.UserService

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

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): Account {
        return userService.getUser(id)
    }

}