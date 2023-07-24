package yapp.be.domain.port.inbound

import java.time.Duration

interface SaveTokenUseCase {

    fun saveTokensWithAuthToken(
        authToken: String,
        accessToken: String,
        refreshToken: String,
        authTokenExpire: Duration
    )
    fun saveToken(
        prefix: String = "",
        token: String,
        value: String,
        expire: Duration,
    )
}
