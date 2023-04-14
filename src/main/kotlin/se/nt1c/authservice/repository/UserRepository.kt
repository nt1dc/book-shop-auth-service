package se.nt1c.authservice.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.repository.query.Param
import reactor.core.publisher.Mono
import se.nt1c.authservice.entity.Account

interface UserRepository : R2dbcRepository<Account, Int> {
    @Query(value = "select account.* from account join account_roles ar on account.id = ar.account_id where login= :login")
    fun findByLogin(@Param("login")login: String): Mono<Account?>
}
