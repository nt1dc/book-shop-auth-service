package se.nt1dc.authservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthServiceApplication

fun main(args: Array<String>) {
    runApplication<se.nt1dc.authservice.AuthServiceApplication>(*args)
}
