package yapp.be.apiapplication.volunteer.service.model
data class GetVolunteerMyProfileResponseDto(
    val name: String,
    val historyStat: VolunteerVolunteerEventHistoryStatInfo
)

data class VolunteerVolunteerEventHistoryStatInfo(
    val done: Int,
    val waiting: Int,
    val joining: Int
)
