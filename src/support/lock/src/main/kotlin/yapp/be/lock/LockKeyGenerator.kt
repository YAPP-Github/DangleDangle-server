package yapp.be.lock

import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.BeanWrapperImpl
import org.springframework.stereotype.Component
import yapp.be.exceptions.CustomException
import yapp.be.lock.exceptions.LockExceptionType

@Component
class LockKeyGenerator {

    fun generate(
        arguments: Array<Any>,
        methodSignature: MethodSignature,
        identifiers: Array<String>
    ): String {
        if (identifiers.isEmpty()) {
            throw CustomException(
                type = LockExceptionType.INVALID_LOCK_IDENTIFIERS,
                message = "Lock을 식별 할 Identifiers가 정의되지 않았습니다."

            )
        }

        return identifiers.joinToString(":") { identifier ->
            val isSingleProperty = identifier.indexOf(".") == -1
            if (isSingleProperty) {
                val keyIndex = methodSignature.parameterNames.indexOf(identifier)
                val argument = arguments.getOrNull(keyIndex)
                    ?: throw CustomException(
                        type = LockExceptionType.INVALID_LOCK_IDENTIFIERS,
                        message = "Lock 식별자를 찾을 수 없습니다.${methodSignature.name}:$identifier"
                    )
                getValueOfSingleProperty(argument = argument)
            } else {
                val parameterSeperatedIndex = identifier.indexOf(".")
                val parameterName: String = identifier.substring(0, parameterSeperatedIndex)
                val propertyPath: String = identifier.substring(parameterSeperatedIndex + 1)

                val keyIndex = methodSignature.parameterNames.indexOf(parameterName)
                val argument = arguments.getOrNull(keyIndex)
                    ?: throw CustomException(
                        type = LockExceptionType.INVALID_LOCK_IDENTIFIERS,
                        message = "Lock 식별자를 찾을 수 없습니다.${methodSignature.name}:$identifier"
                    )

                getValueOfNestedProperty(
                    identifier = propertyPath,
                    argument = argument
                )
            }
        }
    }
}

private fun getValueOfSingleProperty(argument: Any): String {
    return argument.toString()
}

private fun getValueOfNestedProperty(identifier: String, argument: Any): String {
    val wrapper = BeanWrapperImpl(argument)
    return if (wrapper.isReadableProperty(identifier)) {
        wrapper.getPropertyValue(identifier).toString()
    } else {
        throw CustomException(
            type = LockExceptionType.INVALID_LOCK_IDENTIFIERS,
            message = "Lock 식별자를 찾을 수 없습니다. $identifier"
        )
    }
}
