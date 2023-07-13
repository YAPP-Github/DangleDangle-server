package yapp.be.storage.jpa.volunteerevent.model

import jakarta.persistence.*
import yapp.be.model.enums.volunteerevent.AgeLimit
import yapp.be.model.enums.volunteerevent.VolunteerEventCategory
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.storage.jpa.common.model.BaseTimeEntity
import java.time.LocalDateTime
import yapp.be.domain.model.VolunteerEvent

@Entity
@Table(
    name = "volunteer_event",
    indexes = [
        Index(name = "IDX_SHELTER_ID", columnList = "shelter_id")
    ]
)
class VolunteerEventEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "shelter_id")
    val shelterId: Long,
    @Column(name = "title")
    var title: String,
    @Column(name = "recruit_num")
    var recruitNum: Int,
    @Column(name = "description", columnDefinition = "TEXT")
    var description: String?,
    @Column(name = "age_limit")
    @Enumerated(EnumType.STRING)
    var ageLimit: AgeLimit,
    @Column(name = "start_at")
    var startAt: LocalDateTime,
    @Column(name = "end_at")
    var endAt: LocalDateTime,
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: VolunteerEventStatus,
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    var category: VolunteerEventCategory,
    @Column(name = "is_deleted")
    var deleted: Boolean = false
) : BaseTimeEntity() {
    fun update(volunteerEvent: VolunteerEvent) {
        this.title = volunteerEvent.title
        this.description = volunteerEvent.description
        this.recruitNum = volunteerEvent.recruitNum
        this.status = volunteerEvent.volunteerEventStatus
        this.category = volunteerEvent.volunteerEventCategory
        this.startAt = volunteerEvent.startAt
        this.endAt = volunteerEvent.endAt
        this.ageLimit = volunteerEvent.ageLimit
    }

    fun delete() {
        this.deleted = true
    }
}
