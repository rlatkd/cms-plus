package kr.or.kosa.cmsplusmain.domain.vendor.service;

import kr.or.kosa.cmsplusmain.domain.vendor.dto.CustomUserDetailsDto;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.Vendor;
import kr.or.kosa.cmsplusmain.domain.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final VendorRepository vendorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Vendor userData = vendorRepository.findByUsername(username);

        if(userData != null) {
            return new CustomUserDetailsDto(userData);
        }

        return null;
    }
}
