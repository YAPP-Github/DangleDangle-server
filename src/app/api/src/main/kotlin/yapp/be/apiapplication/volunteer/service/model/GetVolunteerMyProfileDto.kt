package yapp.be.apiapplication.volunteer.service.model
data class GetVolunteerMyProfileResponseDto(
    val nickName: String,
    val historyStat: VolunteerVolunteerEventHistoryStatInfo,
    val phoneNumber: String,
    val alarmEnabled: Boolean
)

data class VolunteerVolunteerEventHistoryStatInfo(
    val done: Int,
    val waiting: Int,
    val joining: Int
)
