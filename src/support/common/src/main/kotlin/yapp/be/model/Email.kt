package yapp.be.model

data class Email(val value: String) {
    private val EMAIL_REGEX_VALUE = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"
    private val EMAIL_REGEX = EMAIL_REGEX_VALUE.toRegex()
    init {
        if (!EMAIL_REGEX.matches(value)) {
            throw IllegalArgumentException("Invalid Email : $value")
        }
    }
}
