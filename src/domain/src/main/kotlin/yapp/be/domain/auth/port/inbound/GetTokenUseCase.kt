package yapp.be.domain.auth.port.inbound

interface GetTokenUseCase {
    fun getTokensByAuthToken(authToken: String): String?
    fun getTokenByAccessToken(accessToken: String): String?
}
