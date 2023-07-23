package yapp.be.domain.port.outbound

import java.time.Duration

interface TokenCommandHandler {

    fun deleteTokenByAuthToken(authToken: String)
    fun deleteToken(accessToken: String)
    fun saveToken(prefix: String, token: String, value: String, duration: Duration)
    fun saveTokensWithAuthToken(authToken: String, accessToken: String, refreshToken: String, duration: Duration)
}
