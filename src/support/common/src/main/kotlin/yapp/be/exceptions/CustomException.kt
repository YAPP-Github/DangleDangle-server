package yapp.be.exceptions

class CustomException(type: CustomExceptionType, message: String) : RuntimeException(message)
