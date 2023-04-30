package se.nt1dc.authservice.service

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import se.nt1dc.authservice.dto.AuthRequest
import se.nt1dc.authservice.dto.LoginResponse
import se.nt1dc.authservice.dto.RegisterRequest
import se.nt1dc.authservice.dto.UserCreationRequest
import se.nt1dc.authservice.entity.Account
import se.nt1dc.authservice.entity.RoleEnum
import se.nt1dc.authservice.exceptions.BadCreditsException
import se.nt1dc.authservice.exceptions.UserAlreadyExistException
import se.nt1dc.authservice.exceptions.UserCreationException
import se.nt1dc.authservice.exceptions.UserNotFoundException
import se.nt1dc.authservice.repository.RoleRepository
import se.nt1dc.authservice.repository.UserRepository
import se.nt1dc.authservice.utils.JwtTokenUtil

@Service
@Transactional
class UserServiceImpl(
    val userRepository: UserRepository,
    val passwordEncoder: BCryptPasswordEncoder,
    val roleRepository: RoleRepository,
    val jwtTokenUtil: JwtTokenUtil,
    val restTemplate: RestTemplate
) : UserService {
    override fun register(registerRequest: RegisterRequest) {
        val accountOptional = userRepository.findByLogin(registerRequest.login)
        if (accountOptional.isPresent) throw UserAlreadyExistException("user with login: ${registerRequest.login} already exist")
        val roleUser = roleRepository.findByName(RoleEnum.USER)
        val newAccount = Account(
            login = registerRequest.login,
            password = passwordEncoder.encode(registerRequest.password),
            roles = mutableSetOf(roleUser)
        )
        val bookServiceRegistrationResponse = restTemplate.exchange(
            "http://book-service/user", HttpMethod.POST, HttpEntity(
                UserCreationRequest(registerRequest.login)
            ), String::class.java
        )
        if (!bookServiceRegistrationResponse.statusCode.is2xxSuccessful) throw UserCreationException(
            "exception on Book service side ${bookServiceRegistrationResponse.body.toString()}",
        )
        userRepository.save(newAccount)

    }

    override fun login(authRequest: AuthRequest): LoginResponse {
        val account = userRepository.findByLogin(authRequest.login)
            .orElseThrow { UserAlreadyExistException("user with login: ${authRequest.login} doesnt exist") }
        if (!passwordEncoder.matches(
                authRequest.password,
                account.password
            )
        ) throw BadCreditsException(
            ""
        )
        val token =
            jwtTokenUtil.generateToken(account.login, account.roles.stream().map { it.name.toString() }.toList())
        return LoginResponse(token)

    }

    override fun findByLogin(login: String): Account {
        return userRepository.findByLogin(login)
            .orElseThrow { UserNotFoundException("user with login $login nor found") }
    }
}

