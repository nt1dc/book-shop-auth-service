package se.nt1c.authservice.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono
import se.nt1c.authservice.entity.Role
import se.nt1c.authservice.entity.RoleEnum

interface RoleRepository : R2dbcRepository<Role, Int> {
    fun findByName(name: RoleEnum): Mono<Role>
}