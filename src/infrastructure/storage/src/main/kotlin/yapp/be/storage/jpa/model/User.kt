package yapp.be.storage.jpa.model

import jakarta.persistence.*
import yapp.be.storage.jpa.common.BaseTimeEntity

@Entity
@Table(name = "user")
class User(): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    @Column
    val email: String? = null
    @Column
    val nickname: String? = null
    @Column
    val phone: String? = null
    @Column
    val role: ROLE = ROLE.VOLUNTEER
    @Column
    @Enumerated(EnumType.STRING)
    val oAuthType: OAuthType? = null
    @Column
    val oAuthAccessToken: String? = null
    @Column
    val isDeleted: Boolean = false
    @Column
    val shelterIdentifier: String? = null
}

enum class OAuthType {
    GOOGLE, KAKAO
}
enum class ROLE {
    VOLUNTEER, SHELTER_ADMIN
}
