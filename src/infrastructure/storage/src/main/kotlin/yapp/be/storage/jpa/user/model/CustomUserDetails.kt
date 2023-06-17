package yapp.be.storage.jpa.user.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*

class CustomUserDetails(
    var id: Long?,
    private var email: String,
    private var authorities: Collection<GrantedAuthority?>?,
    private var attributes: Map<String, Any>?
) : UserDetails, OAuth2User {
    private fun create(user: UserEntity): CustomUserDetails {
        val authorities: List<GrantedAuthority> = Collections.singletonList(SimpleGrantedAuthority("ROLE_USER"))
        return CustomUserDetails(
            id = user.id,
            email = user.email,
            authorities = authorities,
            null
        )
    }

    fun create(user: UserEntity, attributes: Map<String, Any>): CustomUserDetails? {
        val userDetails: CustomUserDetails = create(user)
        userDetails.setAttributes(attributes)
        return userDetails
    }

    // UserDetail Override
    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return authorities
    }

    override fun getUsername(): String? {
        return email
    }

    override fun getPassword(): String? {
        return null
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    // OAuth2User Override
    override fun getName(): String? {
        return id.toString()
    }

    override fun getAttributes(): Map<String, Any>? {
        return attributes
    }

    fun setAttributes(attributes: Map<String, Any>) {
        this.attributes = attributes
    }
}
