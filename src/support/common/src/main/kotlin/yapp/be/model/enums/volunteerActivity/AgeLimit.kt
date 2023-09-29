package yapp.be.model.enums.volunteerActivity

enum class AgeLimit(
    val description: String
) {
    NONE("나이제한 없음"),
    ELEMENTARY("초등학생 이상"),
    MIDDLE("중학생 이상"),
    HIGH("고등학생 이상"),
    ADULT("성인")
}
