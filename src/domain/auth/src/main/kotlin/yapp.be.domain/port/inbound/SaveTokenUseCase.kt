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
        accessToken: String,
        refreshToken: String,
        expire: Duration,
    )

    fun saveLogoutToken(
        accessToken: String,
        expire: Duration
    )
}
