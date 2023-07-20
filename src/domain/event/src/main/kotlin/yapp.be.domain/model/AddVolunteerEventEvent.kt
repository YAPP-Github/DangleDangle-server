package yapp.be.domain.model

// 북마크한 보호소의 새로운 봉사이벤트가 생성되었을 때 발행
data class AddVolunteerEventEvent (
    val id: Long = 0,
    val volunteerId : Long,
    val shelterId: Long,
    val volunteerEventId: Long,
): Event()
