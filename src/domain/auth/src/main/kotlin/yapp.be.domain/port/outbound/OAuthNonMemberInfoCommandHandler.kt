package yapp.be.domain.port.outbound

import yapp.be.model.Email
import java.time.Duration

interface OAuthNonMemberInfoCommandHandler {
    fun save(email: Email, oAuthIdentifier: String, duration: Duration)
}
