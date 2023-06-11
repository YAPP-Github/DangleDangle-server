package yapp.be.storage.jpa.user.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User


class CustomOAuth2User(
    authorities: Collection<GrantedAuthority?>?,
    attributes: Map<String, Any?>, nameAttributeKey: String?,
    private val oAuthAttributes: OAuthAttributes
) : DefaultOAuth2User(authorities, attributes, nameAttributeKey)
