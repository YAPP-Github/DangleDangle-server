package yapp.be.domain.port.outbound

interface TokenCommandHandler {

    fun deleteTokenByAuthToken(authToken: String)
    fun deleteToken(accessToken: String)
    fun saveToken(accessToken: String, refreshToken: String, expire: Long)

    fun saveTokensWithAuthToken(authToken: String, accessToken: String, refreshToken: String, expire: Long)
}
