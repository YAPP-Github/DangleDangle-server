package yapp.be.enums.volunteerevent

enum class VolunteerEventStatus(
    private val description: String
) {
    IN_PROGRESS("모집중"),
    CLOSED("모집완료"),
    DONE("종료")
}
