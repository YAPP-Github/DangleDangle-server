package yapp.be.domain.port.outbound

interface TokenQueryHandler {
    fun getTokensByAuthToken(authToken: String): String?
    fun getTokenByAccessToken(prefix: String = "", accessToken: String): String?
}
