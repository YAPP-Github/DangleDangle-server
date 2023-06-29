package yapp.be.domain.port.inbound

interface SaveTokenUseCase {

    fun saveTokensWithAuthToken(
        authToken: String,
        accessToken: String,
        refreshToken: String,
        authTokenExpire: Long
    )
    fun saveToken(
        accessToken: String,
        refreshToken: String,
        expire: Long,
    )
}
