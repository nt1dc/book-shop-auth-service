package se.nt1c.authservice.entity

import jakarta.persistence.*

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column(unique = true)
    var login: String,
    var password: String,
    @ManyToMany
    @JoinTable(
        name = "account_roles",
        joinColumns = [JoinColumn(
            name = "account_id", referencedColumnName = "id"
        )],
        inverseJoinColumns = [JoinColumn(
            name = "role_id", referencedColumnName = "id"
        )]
    )
    var roles: MutableSet<Role>
)