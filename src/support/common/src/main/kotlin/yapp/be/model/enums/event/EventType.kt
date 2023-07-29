package yapp.be.model.enums.event

enum class EventType(
    private val description: String,
) {
    UPDATE("봉사 이벤트 정보 변경"),
    VOLUNTEER_REMINDER("봉사자 봉사 이벤트 리마인더"),
    SHELTER_REMINDER("보호소 봉사 이벤트 리마인더"),
    DELETE("봉사 이벤트 삭제"),
    ENABLE_JOIN("봉사 이벤트 참여 가능"),
}
