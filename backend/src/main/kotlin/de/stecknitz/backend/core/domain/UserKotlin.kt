package de.stecknitz.backend.core.domain

import jakarta.persistence.*

@Entity
@Table(name = "user", schema = "public")
class UserKotlin(
    @Id
    var email: String,
    @Column(name = "first_name")
    var firstname: String,
    @Column(name = "last_name")
    var lastname: String,
    @Column(name = "password")
    var password: String,
    @ManyToOne
    @JoinColumn(name = "role")
    var role: Role?,
    @Column(name = "salt")
    var salt: String,
    @OneToMany(mappedBy = "user")
    var depots: MutableList<Depot>?
)