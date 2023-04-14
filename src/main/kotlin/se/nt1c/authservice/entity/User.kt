package se.nt1c.authservice.entity

import jakarta.persistence.*

@Entity
@Table(name = "\"user\"")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column(unique = true)
    var login: String,
    var password: String,
    var roles: MutableSet<Role>
)