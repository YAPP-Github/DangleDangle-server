package yapp.be.domain.common.port

import yapp.be.domain.common.model.AlimtalkMessageTemplate

interface KakaoNotificationHandler {
    fun request(
        variables: Map<String, Map<String, String>>,
        template: AlimtalkMessageTemplate
    )
}
