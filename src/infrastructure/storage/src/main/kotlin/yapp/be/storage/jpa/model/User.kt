package yapp.be.storage.jpa.model

import jakarta.persistence.*
import yapp.be.enum.OAuthType
import yapp.be.enum.Role
import yapp.be.storage.jpa.common.BaseTimeEntity

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column
    val email: String,
    @Column
    val nickname: String,
    @Column
    val phone: String,
    @Column
    val role: Role = Role.VOLUNTEER,
    @Column
    @Enumerated(EnumType.STRING)
    val oAuthType: OAuthType,
    @Column
    val oAuthAccessToken: String,
    @Column
    val isDeleted: Boolean = false,
    @Column
    val shelterId: String,
): BaseTimeEntity()
