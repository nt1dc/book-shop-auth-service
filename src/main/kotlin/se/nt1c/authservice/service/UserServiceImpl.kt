package se.nt1c.authservice.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import se.nt1c.authservice.dto.AuthRequest
import se.nt1c.authservice.dto.RegisterRequest
import se.nt1c.authservice.entity.Role
import se.nt1c.authservice.entity.User
import se.nt1c.authservice.repository.UserRepository

@Service
class UserServiceImpl(
    val userRepository: UserRepository, val passwordEncoder: BCryptPasswordEncoder
) : UserService {
    override fun register(registerRequest: RegisterRequest) {
        val zser = User(
            login = registerRequest.login,
            password = passwordEncoder.encode(registerRequest.password),
            roles = mutableSetOf(Role.USER)
        )
        userRepository.save(zser)
    }

    override fun login(authRequest: AuthRequest): Mono<String> {
        val userMono = userRepository.findByLogin(authRequest.login)
        userMono.doOnSuccess {
//            if (passwordEncoder.matches(authRequest.password, it.password))
        }
        return Mono.just("xzc")
    }

    override fun getUser(id: Int): Mono<User> {
        return userRepository.findById(id)
    }
}