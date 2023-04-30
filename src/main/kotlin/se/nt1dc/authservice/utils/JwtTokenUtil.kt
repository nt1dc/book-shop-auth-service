package se.nt1dc.authservice.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import se.nt1dc.authservice.exceptions.InvalidTokenException
import java.security.Key
import java.util.*


@Component
class JwtTokenUtil
    (
    @Value("\${jwt.token.life_expectancy}") seconds: Long,
    @Value("\${jwt.token.secret}") private var secretWord: String
) {
    private var LIFE_EXPECTANCY: Long = seconds
    var secretKey: Key = Keys.hmacShaKeyFor(secretWord.encodeToByteArray())


    /**
     * Method generates token and sets a claim with user login
     *
     * @param login user login
     * @return JWT token
     */
    fun generateToken(login: String?, roles: MutableList<String>): String {
        return Jwts.builder()
            .setIssuer("app")
            .setSubject(login)
            .setIssuedAt(Date())
            .claim("roles", roles)
            .setExpiration(Date(System.currentTimeMillis() + LIFE_EXPECTANCY * 1000))
            .signWith(secretKey).compact()
    }

    /**
     * Method gets login from string
     *
     * @param token string token
     * @return user login
     */
    fun getLogin(token: String): String {
        return getAllClaims(token).subject
    }


    /**
     * Method gets token expiration date
     *
     * @param token token string
     * @return token expiration date
     */
    fun getExpirationDate(token: String): Date {
        return getAllClaims(token).expiration
    }

    /**
     * Method checks whether token is expired or not
     *
     * @param token token string
     * @return true if token is expired, false if it's not
     */
    fun isTokenExpired(token: String): Boolean {
        return getExpirationDate(token).before(Date())
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body
    }


    fun extractToken(token: String): String {
        if (token.startsWith("Bearer ") && token.length > 7) {
            return token.substring(7)
        } else {
            throw InvalidTokenException("invalid token")
        }
    }

    fun validateToken(jwtToken: String) {
        if (isTokenExpired(jwtToken)) throw InvalidTokenException("token expired")
    }


}
