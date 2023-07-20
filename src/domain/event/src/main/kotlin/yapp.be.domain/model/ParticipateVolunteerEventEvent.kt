package yapp.be.domain.model

// 정상적으로 봉사 이벤트를 참여했을 때 발행
data class ParticipateVolunteerEventEvent (
    val shelterId: Long,
    val volunteerEventId: Long,
    val volunteerId: Long,
): Event()
