package yapp.be.storage.config.exceptions

import yapp.be.exceptions.CustomExceptionType

enum class StorageExceptionType(override val code: String) : CustomExceptionType {
    ENTITY_NOT_FOUND("STORAGE-001")
}
