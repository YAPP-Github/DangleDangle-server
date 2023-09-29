package yapp.be.domain.auth.port.inbound

interface CheckTokenUseCase {
    fun isValidRefreshToken(
        accessToken: String,
        refreshToken: String,
    ): Boolean

    fun isTokenBlackList(
        accessToken: String,
    ): Boolean
}
