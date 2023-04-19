package se.nt1c.authservice.repository

import org.springframework.data.jpa.repository.JpaRepository
import se.nt1c.authservice.entity.Role
import se.nt1c.authservice.entity.RoleEnum

interface RoleRepository : JpaRepository<Role, Int> {
    fun findByName(name: RoleEnum): Role
}