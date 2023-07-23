package yapp.be.domain.port.inbound

interface CheckTokenUseCase {
    fun isValidRefreshToken(
        accessToken: String,
        refreshToken: String,
    ): Boolean

    fun isTokenBlackList(
        accessToken: String,
    ): Boolean
}
