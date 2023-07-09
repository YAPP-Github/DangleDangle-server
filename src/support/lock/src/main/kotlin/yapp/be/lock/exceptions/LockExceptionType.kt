package yapp.be.lock.exceptions

import yapp.be.exceptions.CustomExceptionType

enum class LockExceptionType(
    override val code: String
) : CustomExceptionType {
    INVALID_LOCK_IDENTIFIERS("LOCK-001"),
    LOCK_ACQUIRED_FAILED("LOCK-002")
}
