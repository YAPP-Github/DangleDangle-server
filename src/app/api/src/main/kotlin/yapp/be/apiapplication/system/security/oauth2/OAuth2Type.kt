package yapp.be.apiapplication.system.security.oauth2

import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.exceptions.CustomException

enum class OAuth2Type {
    KAKAO;

    companion object {
        fun of(registrationId: String): OAuth2Type {
            return when (registrationId) {
                "kakao" -> KAKAO
                else -> throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "지원하지 않는 OAuth2 로그인입니다.")
            }
        }
    }
}
