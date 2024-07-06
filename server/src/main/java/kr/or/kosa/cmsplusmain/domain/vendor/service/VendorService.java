package kr.or.kosa.cmsplusmain.domain.vendor.service;

import kr.or.kosa.cmsplusmain.domain.vendor.dto.SignupDto;
import kr.or.kosa.cmsplusmain.domain.vendor.entity.UserRole;
import kr.or.kosa.cmsplusmain.domain.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VendorService {
    private final VendorRepository vendorRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void join(SignupDto signupDto) {
        String username = signupDto.getUsername();
        String password = bCryptPasswordEncoder.encode(signupDto.getPassword());
        UserRole role = UserRole.ROLE_VENDOR;

        boolean isExist = vendorRepository.isExistUsername(username);
        if(isExist){
            throw new RuntimeException("Username already exists.");
        }

        vendorRepository.save(signupDto.toEntity(password, role));
    }

    public boolean isExistUsername(String username) {
        boolean isExist = vendorRepository.isExistUsername(username);
        return vendorRepository.isExistUsername(username);
    }
}
