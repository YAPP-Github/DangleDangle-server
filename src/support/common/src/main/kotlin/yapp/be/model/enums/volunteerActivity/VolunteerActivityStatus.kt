package yapp.be.model.enums.volunteerActivity

enum class VolunteerActivityStatus(
    private val description: String
) {
    IN_PROGRESS("모집중"),
    CLOSED("모집완료"),
    DONE("종료")
}
