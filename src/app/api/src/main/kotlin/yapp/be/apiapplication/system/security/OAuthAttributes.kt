package yapp.be.apiapplication.system.security

class OAuthAttributes(val attributes: Map<String, Any>, val nameAttributeKey: String, val email: String) {
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
