package yapp.be.apiapplication.system.security.oauth2

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import yapp.be.model.enums.volunteerevent.Role
import java.util.*

class CustomOAuth2User(
    val provider: OAuth2Type,
    val oAuth2User: OAuth2User,
    val customOAuthAttributes: CustomOAuthAttributes
) : OAuth2User {
    override fun getName(): String {
        return oAuth2User.name
    }

    override fun getAttributes(): Map<String, Any> {
        return oAuth2User.attributes
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return Collections.singletonList(SimpleGrantedAuthority(Role.VOLUNTEER.name))
    }
}
