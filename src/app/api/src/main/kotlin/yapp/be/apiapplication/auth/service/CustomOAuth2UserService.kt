package yapp.be.apiapplication.auth.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import yapp.be.apiapplication.system.security.CustomOAuth2User
import yapp.be.apiapplication.system.security.OAuthAttributes
import yapp.be.enum.Role

@Service
class CustomOAuth2UserService : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)
        val attributes: OAuthAttributes = OAuthAttributes.of(oAuth2User.attributes)
        return CustomOAuth2User(
            setOf(SimpleGrantedAuthority(Role.VOLUNTEER.name)),
            attributes.attributes,
            attributes.nameAttributeKey,
            attributes
        )
    }
}
