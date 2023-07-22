package yapp.be.model.enums.event

enum class EventType(
    private val description: String,
) {
    ADD("봉사 이벤트 추가"),
    PARTICIPATE("봉사 이벤트 참여"),
    WITHDRAW("봉사 이벤트 철회"),
}
