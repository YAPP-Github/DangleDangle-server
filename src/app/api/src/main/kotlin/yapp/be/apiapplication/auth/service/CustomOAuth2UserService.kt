package yapp.be.apiapplication.auth.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import yapp.be.domain.service.CheckUserService
import yapp.be.enum.Role
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType
import yapp.be.storage.jpa.user.model.CustomOAuth2User
import yapp.be.storage.jpa.user.model.OAuthAttributes
@Service
class CustomOAuth2UserService(
    private val checkUserService: CheckUserService
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oAuth2User = delegate.loadUser(userRequest)
        val attributes: OAuthAttributes = OAuthAttributes.of(oAuth2User.attributes)

        if (!checkUserService.isExist(attributes.email)) {
            throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.")
        }

        return CustomOAuth2User(
            setOf(SimpleGrantedAuthority(Role.VOLUNTEER.name)),
            attributes.attributes,
            attributes.nameAttributeKey,
            attributes
        )
    }
}
