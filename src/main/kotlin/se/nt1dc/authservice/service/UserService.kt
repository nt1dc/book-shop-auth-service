package se.nt1dc.authservice.service

import org.springframework.stereotype.Service
import se.nt1dc.authservice.dto.AuthRequest
import se.nt1dc.authservice.dto.LoginResponse
import se.nt1dc.authservice.dto.RegisterRequest
import se.nt1dc.authservice.entity.Account


@Service
interface UserService {
    fun register(registerRequest: se.nt1dc.authservice.dto.RegisterRequest)
    fun login(authRequest: se.nt1dc.authservice.dto.AuthRequest): se.nt1dc.authservice.dto.LoginResponse
    fun findByLogin(login: String): se.nt1dc.authservice.entity.Account
}