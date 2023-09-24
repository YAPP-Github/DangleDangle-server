package yapp.be.domain.auth.port.inbound

interface DeleteTokenUseCase {

    fun deleteTokenByAuthToken(
        authToken: String
    )
    fun deleteToken(
        accessToken: String,
    )
}
