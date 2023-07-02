package yapp.be.domain.port.inbound

import yapp.be.model.Email
import java.time.Duration

interface SaveOAuthNonMemberInfoUseCase {
    fun saveOAuthNonMemberInfo(
        email: Email,
        oAuthIdentifier: String,
        duration: Duration
    )
}
