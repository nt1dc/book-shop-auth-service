package se.nt1c.authservice.controller

import io.jsonwebtoken.JwtException
import org.apache.hc.core5.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import se.nt1c.authservice.exceptions.InvalidTokenException
import se.nt1c.authservice.exceptions.NotEnoughAnchorites
import se.nt1c.authservice.exceptions.UserAlreadyExistException


@ControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException::class)
    fun userAlreadyExist(ex: UserAlreadyExistException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(ex.message)
    }

    @ExceptionHandler(NotEnoughAnchorites::class)
    fun userAlreadyExist(ex: NotEnoughAnchorites): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).body(ex.message)
    }

    @ExceptionHandler(InvalidTokenException::class)
    fun userAlreadyExist(ex: InvalidTokenException): ResponseEntity<Any> {
        ex.printStackTrace()
        return ResponseEntity.status(498).body(ex.message)
    }

    @ExceptionHandler(JwtException::class)
    fun jwtExceptionHandler(ex: JwtException): ResponseEntity<String> {
        println(ex.printStackTrace())
        return ResponseEntity.status(401).body(ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun exception(ex: Exception): ResponseEntity<String> {
        println(ex.printStackTrace())
        return ResponseEntity.status(500).body(ex.message)
    }


}