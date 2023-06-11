package yapp.be.storage.jpa.user.model

import yapp.be.enum.OAuthType

class OAuthAttributes(val attributes: Map<String, Any>, val nameAttributeKey: String, val email: String) {
    fun toEntity(): UserEntity {
        return UserEntity(
            email = email,
            nickname = "",
            phone = "",
            oAuthType = OAuthType.KAKAO,
            oAuthAccessToken = "",
            shelterId = ""
        )
    }
    companion object {
        fun of(
            attributes: Map<String, Any>
        ): OAuthAttributes {
            return ofKakao("id", attributes)
        }

        private fun ofKakao(
            userNameAttributeName: String,
            attributes: Map<String, Any>
        ): OAuthAttributes {
            val kakaoAccount = attributes["kakao_account"] as Map<String, Any>
            return OAuthAttributes(
                email = kakaoAccount["email"] as String,
                attributes = attributes,
                nameAttributeKey = userNameAttributeName
            )
        }
    }
}
