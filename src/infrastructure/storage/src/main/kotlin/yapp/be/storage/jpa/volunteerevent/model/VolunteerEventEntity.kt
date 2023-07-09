package yapp.be.storage.jpa.volunteerevent.model

import jakarta.persistence.*
import yapp.be.enums.volunteerevent.AgeLimit
import yapp.be.enums.volunteerevent.VolunteerEventCategory
import yapp.be.enums.volunteerevent.VolunteerEventStatus
import yapp.be.storage.jpa.common.model.BaseTimeEntity
import java.time.LocalDateTime

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
    val title: String,
    @Column(name = "recruit_num")
    val recruitNum: Int,
    @Column(name = "description", columnDefinition = "TEXT")
    var description: String?,
    @Column(name = "age_limit")
    @Enumerated(EnumType.STRING)
    val ageLimit: AgeLimit,
    @Column(name = "start_at")
    val startAt: LocalDateTime,
    @Column(name = "end_at")
    val endAt: LocalDateTime,
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: VolunteerEventStatus,
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    val category: VolunteerEventCategory
) : BaseTimeEntity()
