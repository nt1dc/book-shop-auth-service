package se.nt1dc.authservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import se.nt1dc.authservice.entity.Role
import se.nt1dc.authservice.entity.RoleEnum

interface RoleRepository : JpaRepository<se.nt1dc.authservice.entity.Role, Int> {
    fun findByName(name: se.nt1dc.authservice.entity.RoleEnum): se.nt1dc.authservice.entity.Role
}