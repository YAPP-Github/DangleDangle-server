package yapp.be.storage.jpa.volunteer.model

import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import yapp.be.domain.model.Volunteer
import yapp.be.model.enums.volunteerevent.OAuthType
import yapp.be.model.enums.volunteerevent.Role
import yapp.be.storage.jpa.common.model.BaseTimeEntity

@Entity
@Table(name = "volunteer")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE volunteer SET is_deleted = true WHERE id = ?")
class VolunteerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "email", unique = true, nullable = false)
    val email: String,
    @Column(name = "nickname", unique = true, nullable = false)
    var nickname: String,
    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    val role: Role = Role.VOLUNTEER,
    @Column(name = "phone", nullable = false)
    var phone: String,
    @Column(name = "o_auth_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var oAuthType: OAuthType = OAuthType.KAKAO,
    @Column(name = "o_auth_identifier", nullable = false)
    val oAuthIdentifier: String,
    @Column(name = "o_auth_access_token")
    var oAuthAccessToken: String? = null,
    @Column(name = "o_auth_refresh_token")
    var oAuthRefreshToken: String? = null,
    @Column(name = "is_deleted", nullable = false)
    var deleted: Boolean = false,
    @Column(name = "is_alarm_enabled", nullable = false)
    val alarmEnabled: Boolean = true,
    @Column(name = "is_personal_information_terms_enabled", nullable = false)
    val personalInformationTermsEnabled: Boolean = true,
    @Column(name = "is_service_terms_enabled")
    val serviceTermsEnabled: Boolean = true

) : BaseTimeEntity() {
    fun update(volunteer: Volunteer) {
        this.nickname = volunteer.nickname
        this.phone = volunteer.phone
        this.oAuthType = volunteer.oAuthType
        this.oAuthAccessToken = volunteer.oAuthAccessToken
        this.oAuthRefreshToken = volunteer.oAuthRefreshToken
    }
    fun delete() {
        this.deleted = true
    }
}
