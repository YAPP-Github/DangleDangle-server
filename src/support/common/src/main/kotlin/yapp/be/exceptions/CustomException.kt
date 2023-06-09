package yapp.be.exceptions

class CustomException(val type: CustomExceptionType, override val message: String) : RuntimeException(message)
