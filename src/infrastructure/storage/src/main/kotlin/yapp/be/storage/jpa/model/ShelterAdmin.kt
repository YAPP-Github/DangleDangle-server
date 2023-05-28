package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "shelter_admin")
class ShelterAdmin (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val loginId: String,
    @Column
    val password: String,
    @Column
    val email: String,
    @Column
    val phone: String,
    @Column
    val shelterIdentifier: String,
)
