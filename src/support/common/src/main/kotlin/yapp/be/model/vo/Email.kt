package yapp.be.model.vo

import yapp.be.exceptions.CustomException
import yapp.be.exceptions.SystemExceptionType

data class Email(val value: String) {
    private val EMAIL_REGEX_VALUE = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"
    private val EMAIL_REGEX = EMAIL_REGEX_VALUE.toRegex()
    init {
        if (!EMAIL_REGEX.matches(value)) {
            throw CustomException(type = SystemExceptionType.RUNTIME_EXCEPTION, "올바르지 않은 형식 입니다: $value")
        }
    }
}
