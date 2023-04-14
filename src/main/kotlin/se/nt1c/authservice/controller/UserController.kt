package se.nt1c.authservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorReturn
import se.nt1c.authservice.dto.AuthRequest
import se.nt1c.authservice.dto.RegisterRequest
import se.nt1c.authservice.entity.Account
import se.nt1c.authservice.exceptions.UserAlreadyExistException
import se.nt1c.authservice.service.UserService

@RestController
class UserController(val userService: UserService) {
    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): Mono<Account> {
        return userService.register(registerRequest)
    }

    @PostMapping("/login")
    fun login(authRequest: AuthRequest): Mono<String> {
        return userService.login(authRequest)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): Mono<Account> {
        return userService.getUser(id)
    }

}