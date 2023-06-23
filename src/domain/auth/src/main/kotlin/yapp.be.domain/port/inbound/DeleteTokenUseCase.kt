package yapp.be.domain.port.inbound

interface DeleteTokenUseCase {
    fun deleteToken(
        accessToken: String,
    )
}
