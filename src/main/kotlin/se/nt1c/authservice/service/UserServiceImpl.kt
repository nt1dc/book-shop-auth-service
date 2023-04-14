package se.nt1c.authservice.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import se.nt1c.authservice.dto.AuthRequest
import se.nt1c.authservice.dto.RegisterRequest
import se.nt1c.authservice.entity.Account
import se.nt1c.authservice.entity.Role
import se.nt1c.authservice.entity.RoleEnum
import se.nt1c.authservice.exceptions.UserAlreadyExistException
import se.nt1c.authservice.repository.RoleRepository
import se.nt1c.authservice.repository.UserRepository

@Service
class UserServiceImpl(
    val userRepository: UserRepository, val passwordEncoder: BCryptPasswordEncoder,
    val roleRepository: RoleRepository
) : UserService {
    override fun register(registerRequest: RegisterRequest): Mono<Account> {
        return userRepository.findByLogin(registerRequest.login)
            .log()
            .flatMap<Account> { Mono.error(UserAlreadyExistException("user with login: ${registerRequest.login} already exist")) }
            .switchIfEmpty(
                (roleRepository.findByName(RoleEnum.USER).switchIfEmpty { Mono.error(EntityNotFoundException()) }
                    .flatMap {
                        userRepository.save(
                            Account(
                                login = registerRequest.login,
                                password = passwordEncoder.encode(registerRequest.password),
                                roles = mutableSetOf(it)
                            )
                        )
                    })
            )
    }

    override fun login(authRequest: AuthRequest): Mono<String> {
        TODO("Not yet implemented")
    }

    override fun getUser(id: Int): Mono<Account> {
        TODO("Not yet implemented")
    }

}


//        if (userRepository.existsAccountByLogin(registerRequest.login).not()) {
//            val zser = Account(
//                login = registerRequest.login,
//                password = passwordEncoder.encode(registerRequest.password),
////            roles = mutableSetOf(Role.USER)
//            )
//            return userRepository.save(zser)
//        } else {
//            throw UserAlreadyExistException("user with login: ${registerRequest.login} already exist")
//        }


