package yapp.be.storage.jpa.volunteer.model

import jakarta.persistence.*
import yapp.be.domain.model.Volunteer
import yapp.be.enum.OAuthType
import yapp.be.enum.Role
import yapp.be.storage.jpa.common.model.BaseTimeEntity

@Entity
@Table(name = "volunteer")
class VolunteerEntity(
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
    fun update(volunteer: Volunteer) {
        this.nickname = volunteer.nickname
        this.phone = volunteer.phone
        this.oAuthType = volunteer.oAuthType
        this.oAuthAccessToken = volunteer.oAuthAccessToken
        this.oAuthRefreshToken = volunteer.oAuthRefreshToken
        this.deleted = volunteer.isDeleted
    }
}
