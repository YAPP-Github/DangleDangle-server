package yapp.be.apiapplication.volunteer.service.model

data class GetVolunteerBookMarkedShelterResponseDto(
    val shelters: List<BookMarkedShelterInfo>
)

data class BookMarkedShelterInfo(
    val shelterId: Long,
    val shelterName: String,
    val shelterProfileImageUrl: String?
)
