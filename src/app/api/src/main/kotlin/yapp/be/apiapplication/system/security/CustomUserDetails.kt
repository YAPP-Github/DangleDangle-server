package yapp.be.apiapplication.system.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    var id: Long?,
    private var email: String,
    private var authorities: Collection<GrantedAuthority>,
    private var attributes: Map<String, Any>?
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getUsername(): String {
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
    private fun setAttributes(attributes: Map<String, Any>) {
        this.attributes = attributes
    }
}
