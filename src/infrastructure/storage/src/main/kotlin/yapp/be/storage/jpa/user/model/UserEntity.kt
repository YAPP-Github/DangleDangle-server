package yapp.be.storage.jpa.user.model

import jakarta.persistence.*
import yapp.be.domain.model.User
import yapp.be.enum.OAuthType
import yapp.be.enum.Role
import yapp.be.storage.jpa.common.model.BaseTimeEntity

@Entity
@Table(name = "user")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "email", unique = true)
    val email: String,
    @Column(name = "nickname", unique = true)
    var nickname: String,
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    val role: Role = Role.VOLUNTEER,
    @Column(name = "phone")
    var phone: String,
    @Column(name = "o_auth_type")
    @Enumerated(EnumType.STRING)
    var oAuthType: OAuthType = OAuthType.KAKAO,
    @Column(name = "o_auth_access_token")
    var oAuthAccessToken: String? = null,
    @Column(name = "o_auth_refresh_token")
    var oAuthRefreshToken: String? = null,
    @Column(name = "is_deleted")
    var deleted: Boolean = false,
) : BaseTimeEntity() {
    fun update(user: User) {
        this.nickname = user.nickname
        this.phone = user.phone
        this.oAuthType = user.oAuthType
        this.oAuthAccessToken = user.oAuthAccessToken
        this.oAuthRefreshToken = user.oAuthRefreshToken
        this.deleted = user.isDeleted
    }
}
