package yapp.be.exceptions

enum class SystemExceptionType(override val code: String) : CustomExceptionType {
    INTERNAL_SERVER_ERROR("SYSTEM-000"),
    RUNTIME_EXCEPTION("SYSTEM-001")
}
