package se.nt1c.authservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import se.nt1c.authservice.entity.Account
import java.util.Optional

interface UserRepository : JpaRepository<Account, Int> {
    fun findByLogin(login: String): Optional<Account>
}
