package yapp.be.domain.auth.port.outbound

interface TokenQueryHandler {
    fun getTokensByAuthToken(authToken: String): String?
    fun getTokenByAccessToken(prefix: String = "", accessToken: String): String?
}
