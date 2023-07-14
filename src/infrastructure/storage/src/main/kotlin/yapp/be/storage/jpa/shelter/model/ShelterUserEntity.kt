package yapp.be.storage.jpa.shelter.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import yapp.be.storage.jpa.common.model.BaseTimeEntity

@Entity
@Table(name = "shelter_user")
class ShelterUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "email", unique = true, nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "shelter_id", nullable = false)
    val shelterId: Long,
) : BaseTimeEntity()
