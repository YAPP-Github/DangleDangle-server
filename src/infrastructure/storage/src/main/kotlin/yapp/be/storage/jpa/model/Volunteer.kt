package yapp.be.storage.jpa.model

import jakarta.persistence.*
import yapp.be.storage.jpa.common.BaseTimeEntity

@Entity
@Table(name = "volunteer")
class Volunteer(): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    @Column
    val identifier: String? = null
    @Column
    val email: String? = null
    @Column
    val nickname: String? = null
    @Column
    val phone: String? = null
    @Column
    @Enumerated(EnumType.STRING)
    val oAuthType: OAuthType? = null
    @Column
    val isDeleted: Boolean = false
}

enum class OAuthType {
    GOOGLE, KAKAO
}
