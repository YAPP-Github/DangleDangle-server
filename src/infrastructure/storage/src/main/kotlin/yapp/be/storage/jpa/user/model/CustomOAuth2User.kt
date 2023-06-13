package yapp.be.storage.jpa.user.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User

class CustomOAuth2User(
    private val authorities: Collection<GrantedAuthority?>?,
    private val attributes: Map<String, Any?>,
    private val nameAttributeKey: String?,
    val oAuthAttributes: OAuthAttributes
) : DefaultOAuth2User(authorities, attributes, nameAttributeKey)
