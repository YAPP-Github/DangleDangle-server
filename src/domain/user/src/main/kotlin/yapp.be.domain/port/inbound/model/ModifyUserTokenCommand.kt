package yapp.be.domain.port.inbound.model

import yapp.be.domain.model.OAuth2SecurityToken

data class ModifyUserTokenCommand(
    val userId: Long,
    val oAuth2SecurityToken: OAuth2SecurityToken
)
