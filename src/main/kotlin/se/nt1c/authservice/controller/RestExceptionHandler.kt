package se.nt1c.authservice.controller

import org.apache.hc.core5.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import se.nt1c.authservice.exceptions.UserAlreadyExistException


@ControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException::class)
    fun userAlreadyExist(ex: UserAlreadyExistException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.SC_CONFLICT).body(ex.message)
    }

}