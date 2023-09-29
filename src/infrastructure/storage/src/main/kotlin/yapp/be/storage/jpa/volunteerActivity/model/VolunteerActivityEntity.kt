package yapp.be.storage.jpa.volunteerActivity.model

import jakarta.persistence.*
import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.model.enums.volunteerActivity.AgeLimit
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.storage.jpa.common.model.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(
    name = "volunteer_activity",
    indexes = [
        Index(name = "IDX_SHELTER_ID", columnList = "shelter_id")
    ]
)
class VolunteerActivityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "shelter_id", nullable = false)
    val shelterId: Long,
    @Column(name = "title", nullable = false)
    var title: String,
    @Column(name = "recruit_num", nullable = false)
    var recruitNum: Int,
    @Column(name = "description", columnDefinition = "TEXT")
    var description: String?,
    @Column(name = "age_limit", nullable = false)
    @Enumerated(EnumType.STRING)
    var ageLimit: AgeLimit,
    @Column(name = "start_at", nullable = false)
    var startAt: LocalDateTime,
    @Column(name = "end_at", nullable = false)
    var endAt: LocalDateTime,
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: VolunteerActivityStatus,
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    var category: VolunteerActivityCategory,
    @Column(name = "is_deleted")
    var deleted: Boolean = false
) : BaseTimeEntity() {
    fun update(volunteerEvent: VolunteerActivity) {
        this.title = volunteerEvent.title
        this.description = volunteerEvent.description
        this.recruitNum = volunteerEvent.recruitNum
        this.status = volunteerEvent.volunteerActivityStatus
        this.category = volunteerEvent.volunteerActivityCategory
        this.startAt = volunteerEvent.startAt
        this.endAt = volunteerEvent.endAt
        this.ageLimit = volunteerEvent.ageLimit
    }

    fun delete() {
        this.deleted = true
    }
}
