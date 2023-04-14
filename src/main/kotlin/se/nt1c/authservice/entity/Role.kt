package se.nt1c.authservice.entity

import jakarta.persistence.*

@Table(name = "role")
@Entity
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column(unique = true)
    @Enumerated(value = EnumType.STRING)
    var name: RoleEnum
)

enum class RoleEnum() {
    ADMIN, USER
}