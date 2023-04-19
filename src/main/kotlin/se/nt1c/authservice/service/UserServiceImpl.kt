package se.nt1c.authservice.service

import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import se.nt1c.authservice.dto.AuthRequest
import se.nt1c.authservice.dto.RegisterRequest
import se.nt1c.authservice.dto.UserCreationRequest
import se.nt1c.authservice.entity.Account
import se.nt1c.authservice.entity.RoleEnum
import se.nt1c.authservice.exceptions.BadCreditsException
import se.nt1c.authservice.exceptions.UserAlreadyExistException
import se.nt1c.authservice.exceptions.UserCreationException
import se.nt1c.authservice.exceptions.UserNotFoundException
import se.nt1c.authservice.repository.RoleRepository
import se.nt1c.authservice.repository.UserRepository
import se.nt1c.authservice.utils.JwtTokenUtil

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val passwordEncoder: BCryptPasswordEncoder,
    val roleRepository: RoleRepository,
    val jwtTokenUtil: JwtTokenUtil
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
        val bookServiceRegistrationResponse = RestTemplate().exchange(
            "http://localhost:8087/user", HttpMethod.POST, HttpEntity(
                UserCreationRequest(registerRequest.login)
            ), String::class.java
        )
        if (!bookServiceRegistrationResponse.statusCode.is2xxSuccessful) throw UserCreationException(
            "exception on Book service side ${bookServiceRegistrationResponse.body.toString()}",
        )
        userRepository.save(newAccount)

    }

    override fun login(authRequest: AuthRequest): String {
        val account = userRepository.findByLogin(authRequest.login)
            .orElseThrow { UserAlreadyExistException("user with login: ${authRequest.login} doesnt exist") }
        if (!passwordEncoder.matches(authRequest.password, account.password)) throw BadCreditsException("")
        return jwtTokenUtil.generateToken(account.login, account.roles.stream().map { it.name.toString() }.toList())
    }

    override fun getUser(id: Int): Account {
        TODO("Not yet implemented")
    }

    override fun findByLogin(login: String): Account {
        return userRepository.findByLogin(login)
            .orElseThrow { UserNotFoundException("user with login $login nor found") }
    }
}

