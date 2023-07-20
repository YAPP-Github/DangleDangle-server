package yapp.be.domain.model

// 참여한 봉사 이벤트를 취소했을 때 발행
// 1. 당사자에게 정상적으로 취소되었다고 알림
// 2. 대기자들에게 자리가 비었다고 알림
data class WithDrawVolunteerEventEvent (
    val withDrawVolunteerId: Long,
    val waitingVolunteerId: List<Long>,
    val volunteerEventId: Long,
): Event()
