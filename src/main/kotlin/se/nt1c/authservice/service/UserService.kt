package se.nt1c.authservice.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import se.nt1c.authservice.dto.AuthRequest
import se.nt1c.authservice.dto.RegisterRequest
import se.nt1c.authservice.entity.Account


@Service
interface UserService {
    fun register(registerRequest: RegisterRequest): Mono<Account>
    fun login(authRequest: AuthRequest): Mono<String>
    fun getUser(id: Int): Mono<Account>
}