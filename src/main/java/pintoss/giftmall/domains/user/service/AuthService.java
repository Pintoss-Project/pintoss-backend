package pintoss.giftmall.domains.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.exceptions.client.ConflictException;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.common.exceptions.client.UnauthorizedException;
import pintoss.giftmall.common.oauth.TokenProvider;
import pintoss.giftmall.common.utils.MailService;
import pintoss.giftmall.common.utils.PasswordGenerator;
import pintoss.giftmall.domains.token.service.TokenService;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.dto.LoginRequest;
import pintoss.giftmall.domains.user.dto.LoginResponse;
import pintoss.giftmall.domains.user.dto.RegisterRequest;
import pintoss.giftmall.domains.user.infra.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final MailService mailService;

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("중복된 이메일이 존재합니다.");
        }

        User user = request.toEntity();
        user.updatePassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("이메일을 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), request.getPassword());
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken);

        return LoginResponse.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public String findUserIdByNameAndPhone(String name, String phone) {
        User user = userRepository.findByNameAndPhone(name, phone)
                .orElseThrow(() -> new NotFoundException("해당 정보를 가진 사용자를 찾을 수 없습니다."));
        return user.getEmail();
    }

    @Transactional
    public void findPassword(String name, String email) {
        User user = userRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        String subject = "핀토스 유저 비밀번호";
        String text = user.getName() + "님의 비밀번호는 : " + user.getPassword() + " 입니다.";

        mailService.sendEmail(email, subject, text);
    }

}
