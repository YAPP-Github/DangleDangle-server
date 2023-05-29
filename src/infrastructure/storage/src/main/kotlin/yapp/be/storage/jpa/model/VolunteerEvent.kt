package yapp.be.storage.jpa.model

import jakarta.persistence.*
import yapp.be.storage.jpa.common.BaseTimeEntity
import java.time.LocalDate

@Entity
@Table(name = "volunteer_event")
class VolunteerEvent(): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    // 작성자
    @Column
    val userIdentifier: String? = null
    @Column
    val shelterIdentifier: String? = null
    @Column
    val title: String? = null
    @Column
    val recruitNum: Int? = null
    @Column
    val material: String? = null
    @Column
    val ageLimit: AgeLimit? = null
    @Column
    val date: LocalDate? = null
    @Column
    val viewCnt: Int? = null
    @Column
    val status: Status? = null
    @Column
    val participantNum: Int? = null
}

enum class AgeLimit {
    NONE, ELEMENTARY, MIDDLE, HIGH, ADULT
}

enum class Status {
    IN_PROGRESS, DONE, CANCELED
}
