package yapp.be.domain.port.outbound

interface TokenCommandHandler {
    fun deleteToken(accessToken: String)
    fun saveToken(accessToken: String, refreshToken: String, expire: Long)
}
