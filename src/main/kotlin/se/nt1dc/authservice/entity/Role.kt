package se.nt1dc.authservice.entity

import jakarta.persistence.*

@Entity
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column(unique = true)
    @Enumerated(value = EnumType.STRING)
    var name: RoleEnum
)

