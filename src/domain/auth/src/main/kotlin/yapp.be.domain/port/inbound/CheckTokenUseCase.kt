package yapp.be.domain.port.inbound

interface CheckTokenUseCase {
    fun checkToken(
        accessToken: String,
        refreshToken: String,
    ): Boolean
}
