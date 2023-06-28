package yapp.be.apiapplication.system.security.oauth2

class CustomOAuthAttributes(val attributes: Map<String, Any>, val email: String) {
    companion object {
        fun of(
            attributes: Map<String, Any>
        ): CustomOAuthAttributes {
            return ofKakao(attributes)
        }

        private fun ofKakao(
            attributes: Map<String, Any>
        ): CustomOAuthAttributes {
            val kakaoAccount = attributes["kakao_account"] as Map<String, Any>
            return CustomOAuthAttributes(
                email = kakaoAccount["email"] as String,
                attributes = attributes
            )
        }
    }
}
