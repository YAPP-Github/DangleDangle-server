package yapp.be.domain.model

import java.util.*

data class Identifier(
    val value: String,
) {
    private val UUID_REGEX_VALUE = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"
    private val UUID_REGEX = UUID_REGEX_VALUE.toRegex()
    init {
        if (!UUID_REGEX.matches(value)) {
            throw IllegalArgumentException("Invalid identifier: $value")
        }
    }

    companion object {
        fun generate() = Identifier(UUID.randomUUID().toString())
    }
}
