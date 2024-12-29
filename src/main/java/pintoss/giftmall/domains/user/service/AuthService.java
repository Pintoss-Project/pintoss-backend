package pintoss.giftmall.domains.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.enums.UserRole;
import pintoss.giftmall.common.exceptions.client.ConflictException;
import pintoss.giftmall.common.exceptions.client.NotFoundException;
import pintoss.giftmall.common.exceptions.client.UnauthorizedException;
import pintoss.giftmall.common.oauth.PrincipalDetails;
import pintoss.giftmall.common.oauth.TokenProvider;
import pintoss.giftmall.common.utils.MailService;
import pintoss.giftmall.domains.token.domain.RefreshToken;
import pintoss.giftmall.domains.token.domain.Token;
import pintoss.giftmall.domains.token.infra.RefreshTokenRepository;
import pintoss.giftmall.domains.token.infra.TokenRepository;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.dto.LoginRequest;
import pintoss.giftmall.domains.user.dto.LoginResponse;
import pintoss.giftmall.domains.user.dto.OAuthRegisterRequest;
import pintoss.giftmall.domains.user.dto.RegisterRequest;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final MailService mailService;
    private final TokenRepository tokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("중복된 이메일이 존재합니다.");
        }

        if (checkPhoneDuplicate(request.getPhone())) {
            throw new ConflictException("중복된 전화번호가 존재합니다.");
        }

        User user = request.toEntity(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("아이디 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        if (!user.isActive()) {
            throw new UnauthorizedException("비활성화된 계정입니다.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), request.getPassword());
        String accessToken = tokenProvider.generateAccessToken(authentication);
        //refreshToken 발급 및 accessToken저장.
        String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken);

        //refreshToken 저장 및 업데이트
        saveOrUpdateRefreshToken(user.getUserId(),refreshToken);

        return LoginResponse.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public LoginResponse refreshToken(String accessToken, String refreshToken) {
        // 리프레시 토큰 조회
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new UnauthorizedException("리프레시 토큰이 유효하지 않습니다."));

        // 유효한 경우, 새로운 액세스 토큰 생성
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String username = ((PrincipalDetails) authentication.getPrincipal()).getUsername();

        User user = userRepository.findByName(username)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        // 새로운 at, rt를 발급
        String newAccessToken = tokenProvider.reissueAccessToken(accessToken);
        String newRefreshToken = tokenProvider.generateRefreshToken(authentication, newAccessToken);

        if (refreshTokenRepository.existsByToken(storedToken.getToken())) {
            refreshTokenRepository.delete(storedToken);
        }

        return LoginResponse
                .builder()
                .grantType("bearer")
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    @Transactional
    public void deactivateUser(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        String email = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        user.deactivate();
        userRepository.save(user);
    }

    @Transactional
    public LoginResponse adminLogin(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("아이디 또는 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        if (user.getRole() != UserRole.ADMIN) {
            throw new UnauthorizedException("관리자만 접근할 수 있습니다.");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), request.getPassword());
        //at, rt 발급
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken);

        //기존의 토큰 엔티티에 저장된 값을 확인후 토큰값 업데이트 or 저장.
        saveOrUpdateRefreshToken(user.getUserId(),refreshToken);

        return LoginResponse.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkPhoneDuplicate(String phone) {
        return userRepository.existsByPhone(phone);
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

    @Transactional
    public void updateUser(String email, OAuthRegisterRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        user.updateName(request.getName());
        user.updatePhone(request.getPhone());

        userRepository.save(user);
    }

    @Transactional
    public void resetPassword(String name, String phone, String newPassword) {
        User user = userRepository.findByNameAndPhone(name, phone)
                .orElseThrow(() -> new NotFoundException("해당 정보를 가진 사용자를 찾을 수 없습니다."));

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.updatePassword(encodedPassword);
        userRepository.save(user);
    }
    
    //토큰 업데이트 및 저장
    private void saveOrUpdateRefreshToken(UUID userId, String newToken) {
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(userId)
                .map(existingToken -> existingToken.updateToken(newToken)) // 기존 토큰 업데이트
                .orElseGet(() -> RefreshToken.builder() // 없으면 새로 생성
                        .userId(userId)
                        .token(newToken)
                        .build());
        log.info(refreshTokenEntity);
        refreshTokenRepository.save(refreshTokenEntity); // 저장 (업데이트 또는 삽입)
    }
}  
