package yapp.be.domain.port.inbound

interface DeleteTokenUseCase {

    fun deleteTokenByAuthToken(
        authToken: String
    )
    fun deleteToken(
        accessToken: String,
    )
}
