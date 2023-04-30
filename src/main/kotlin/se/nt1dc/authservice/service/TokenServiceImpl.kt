package se.nt1dc.authservice.service

import io.micrometer.common.util.internal.logging.Slf4JLoggerFactory
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Service
import se.nt1dc.authservice.dto.RequiredAuthorities
import se.nt1dc.authservice.exceptions.NotEnoughAnchorites
import se.nt1dc.authservice.utils.JwtTokenUtil

@Service
class TokenServiceImpl(
    val userService: UserService, val jwtTokenUtil: JwtTokenUtil,
) : TokenService {
    private val log = Slf4JLoggerFactory.getInstance(this.javaClass)
    override fun validate(
        requiredAuthorities: RequiredAuthorities,
        authorizationHeader: String,
        httpServletResponse: HttpServletResponse
    ) {
        log.info("starting token validation")
        val jwtToken = jwtTokenUtil.extractToken(authorizationHeader)
        jwtTokenUtil.validateToken(jwtToken)
        val login = jwtTokenUtil.getLogin(jwtToken)
        val account = userService.findByLogin(login)
        val roleIntersect = requiredAuthorities.roles.map { se.nt1dc.authservice.entity.RoleEnum.valueOf(it) }.toHashSet()
            .intersect(account.roles.stream().map { it.name }.toList().toSet())
        if (roleIntersect.isEmpty()) throw NotEnoughAnchorites()
        httpServletResponse.addHeader("login", account.login)
        log.info("validation successful")

    }

}