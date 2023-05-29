package yapp.be.storage.jpa.model

import jakarta.persistence.*

@Entity
@Table(name = "shelter_outlink")
class ShelterOutlink (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val url: String,
    @Column
    val type: Type,
    @Column
    val shelterIdentifier: String,
)

enum class Type {
    ACCOUNT, INSTAGRAM
}
