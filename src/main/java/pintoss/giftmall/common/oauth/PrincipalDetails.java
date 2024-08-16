package pintoss.giftmall.common.oauth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import pintoss.giftmall.domains.user.domain.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public record PrincipalDetails(
        User user,
        Map<String, Object> attributes,
        String attributeKey
) implements OAuth2User, UserDetails {

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));
    }

//    @Override
//    public String getName() {
//        return attributes.get(attributeKey).toString();
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        System.out.println("Response Data: " + response);

        if (response != null) {
            return (String) response.get("email");
        }
        return (String) attributes.get("email");
    }

    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response != null) {
            return (String) response.get("name");
        }
        return (String) attributes.get("name");
    }

}

