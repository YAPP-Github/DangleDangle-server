package yapp.be.apiapplication.auth.service

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import yapp.be.apiapplication.system.security.oauth2.CustomOAuth2User
import yapp.be.apiapplication.system.security.oauth2.OAuth2Type
import yapp.be.apiapplication.system.security.oauth2.CustomOAuthAttributes

@Service
class CustomOAuth2UserService : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val clientRegistration = userRequest.clientRegistration
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)
        val customAttributes = CustomOAuthAttributes.of(oAuth2User.attributes)
        return CustomOAuth2User(
            oAuth2User = oAuth2User,
            customOAuthAttributes = customAttributes,
            provider = OAuth2Type.of(clientRegistration.registrationId)
        )
    }
}
