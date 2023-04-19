package se.nt1c.authservice.service

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import se.nt1c.authservice.dto.TokenValidationRequest
import se.nt1c.authservice.entity.RoleEnum
import se.nt1c.authservice.exceptions.InvalidTokenException
import se.nt1c.authservice.exceptions.NotEnoughAnchorites
import se.nt1c.authservice.utils.JwtTokenUtil

@Service
class TokenServiceImpl(val userService: UserService, val jwtTokenUtil: JwtTokenUtil) : TokenService {
    override fun validate(
        tokenValidationRequest: TokenValidationRequest, httpServletRequest: HttpServletRequest
    ) {
        val header = httpServletRequest.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ") && header.length > 7) {
            val token = header.substring(7);
            if (jwtTokenUtil.isTokenExpired(token)) throw InvalidTokenException("token expired")
            val login = jwtTokenUtil.getLogin(token)
            val account = userService.findByLogin(login)
            val toList = account.roles.stream().filter {
                tokenValidationRequest.roles.map { roleStringName -> RoleEnum.valueOf(roleStringName) }.toList()
                    .contains(it.name)
            }.toList()
            if (toList.isEmpty()) throw NotEnoughAnchorites()
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