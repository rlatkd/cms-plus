package kr.or.kosa.cmsplusmain.domain.vendor.dto;

import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetailsDto implements UserDetails {

    private final Vendor vendor;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return (vendor.getRole()).name();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return vendor.getPassword();
    }

    @Override
    public String getUsername() {
        return vendor.getUsername();
    }

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
}
