package se.nt1dc.authservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import se.nt1dc.authservice.entity.Account
import java.util.Optional

interface UserRepository : JpaRepository<se.nt1dc.authservice.entity.Account, Int> {
    fun findByLogin(login: String): Optional<se.nt1dc.authservice.entity.Account>
}
