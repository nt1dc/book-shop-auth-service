package se.nt1c.authservice.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import reactor.core.publisher.Mono
import se.nt1c.authservice.entity.User

interface UserRepository : R2dbcRepository<User, Int> {
    fun findByLogin(password: String): Mono<User>
}
