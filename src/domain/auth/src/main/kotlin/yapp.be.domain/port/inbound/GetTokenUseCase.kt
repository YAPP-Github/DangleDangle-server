package yapp.be.domain.port.inbound

interface GetTokenUseCase {
    fun getTokensByAuthToken(authToken: String): String?
    fun getTokenByAccessToken(accessToken: String): String?
}
