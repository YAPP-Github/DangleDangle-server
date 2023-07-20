package yapp.be.model.enums.event

enum class EventStatus (
    private val description: String,
) {
    BEFORE_PROCESSING("처리전"),
    AFTER_PROCESSING("처리후"),
}
