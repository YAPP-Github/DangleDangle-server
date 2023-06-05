package yapp.be.storage.jpa.volunteerevent.model

import jakarta.persistence.*
import yapp.be.enum.AgeLimit
import yapp.be.enum.VolunteerEventStatus
import yapp.be.storage.jpa.common.BaseTimeEntity
import java.time.LocalDate

@Entity
@Table(name = "volunteer_event_entity")
class VolunteerEventEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    // 작성자
    @Column
    val userId: String,
    @Column
    val shelterId: String,
    @Column
    val title: String,
    @Column
    val recruitNum: Int,
    @Column
    val material: String,
    @Column
    val ageLimit: AgeLimit,
    @Column
    val date: LocalDate,
    @Column
    val viewCnt: Int,
    @Column
    val status: VolunteerEventStatus,
    @Column
    val participantNum: Int,
) : BaseTimeEntity()
