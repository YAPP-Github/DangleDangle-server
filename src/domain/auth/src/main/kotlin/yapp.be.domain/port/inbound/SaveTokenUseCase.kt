package yapp.be.domain.port.inbound

interface SaveTokenUseCase {
    fun saveToken(
        accessToken: String,
        refreshToken: String,
        expire: Long,
    )
}
