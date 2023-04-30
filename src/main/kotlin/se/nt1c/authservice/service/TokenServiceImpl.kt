package se.nt1c.authservice.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import se.nt1c.authservice.dto.TokenValidationRequest
import se.nt1c.authservice.entity.RoleEnum
import se.nt1c.authservice.exceptions.InvalidTokenException
import se.nt1c.authservice.exceptions.NotEnoughAnchorites
import se.nt1c.authservice.utils.JwtTokenUtil

@Service
class TokenServiceImpl(val userService: UserService, val jwtTokenUtil: JwtTokenUtil) : TokenService {
    override fun validate(
        tokenValidationRequest: TokenValidationRequest, jwtHeader: String, httpServletResponse: HttpServletResponse
    ) {

        if (jwtHeader.startsWith("Bearer ") && jwtHeader.length > 7) {
            val token = jwtHeader.substring(7);
            if (jwtTokenUtil.isTokenExpired(token)) throw InvalidTokenException("token expired")
            val login = jwtTokenUtil.getLogin(token)
            val account = userService.findByLogin(login)
            val roleIntersect = tokenValidationRequest.roles.map { RoleEnum.valueOf(it) }.toHashSet()
                .intersect(account.roles.stream().map { it.name }.toList().toSet())
            if (roleIntersect.isEmpty()) throw NotEnoughAnchorites()
            httpServletResponse.addHeader("login", account.login)
            println("validation successful")
        } else {
            throw InvalidTokenException("invalid token")
        }
    }


    override fun extractToken(httpServletRequest: HttpServletRequest): String {
        val header = httpServletRequest.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ") && header.length > 7) {
            val token = header.substring(7);
            if (jwtTokenUtil.isTokenExpired(token)) throw InvalidTokenException("token expired")
            return jwtTokenUtil.getLogin(token)
        } else {
            throw InvalidTokenException("invalid token")
        }
    }
}