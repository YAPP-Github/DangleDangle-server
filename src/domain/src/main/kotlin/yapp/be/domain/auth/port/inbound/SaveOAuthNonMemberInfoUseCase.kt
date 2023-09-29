package yapp.be.domain.auth.port.inbound

import yapp.be.model.vo.Email
import java.time.Duration

interface SaveOAuthNonMemberInfoUseCase {
    fun saveOAuthNonMemberInfo(
        email: Email,
        oAuthIdentifier: String,
        duration: Duration
    )
}
