package pintoss.giftmall.domains.user.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.oauth.TokenProvider;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.dto.LoginRequest;
import pintoss.giftmall.domains.user.dto.LoginResponse;
import pintoss.giftmall.domains.user.dto.RegisterRequest;
import pintoss.giftmall.domains.user.dto.UserResponse;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    private static final String TEST_EMAIL = "user@example.com";
    private static final String TEST_PASSWORD = "password";
    private static final String TEST_NAME = "user";
    private static final String TEST_PHONE = "010-1234-5678";

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .email(TEST_EMAIL)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .name(TEST_NAME)
                .phone(TEST_PHONE)
                .build();
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    public void testRegister() {
        RegisterRequest request = RegisterRequest.builder()
                .email("newUser@example.com")
                .password("newPassword")
                .name("newUser")
                .phone("010-9876-5432")
                .build();

        authService.register(request);

        assertTrue(userRepository.existsByEmail("newUser@example.com"));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void testLogin() {
        LoginRequest request = LoginRequest.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();

        LoginResponse response = authService.login(request);

        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
    }

    @Test
    @DisplayName("이메일 중복 찾기 성공 테스트")
    public void testCheckEmailDuplicate() {
        boolean isDuplicate = authService.checkEmailDuplicate(TEST_EMAIL);
        assertTrue(isDuplicate);
    }

    @Test
    @DisplayName("아이디 찾기 성공 테스트")
    public void testFindUserIdByNameAndPhone() {
        String email = authService.findUserIdByNameAndPhone(TEST_NAME, TEST_PHONE);

        assertEquals(TEST_EMAIL, email);
    }

    @Test
    @DisplayName("회원 정보 조회 성공 테스트")
    public void testGetUserInfo() {
        String token = tokenProvider.generateAccessToken(new UsernamePasswordAuthenticationToken(TEST_EMAIL, TEST_PASSWORD));

        UserResponse userResponse = userService.getUserInfo(token);

        assertEquals(TEST_EMAIL, userResponse.getEmail());
        assertEquals(TEST_NAME, userResponse.getName());
    }

    @Test
    @DisplayName("날짜 및 검색어별 회원 필터링 조회 성공 테스트")
    public void testFindUsersByDateAndKeywordPaged() {
        LocalDate now = LocalDate.now();

        Page<UserResponse> users = userService.findUsersByDateAndKeywordPaged(now.minusDays(1), now.plusDays(1), TEST_PHONE, 0, 10);

        assertNotNull(users);
        assertEquals(1, users.getTotalPages());
        assertEquals(1, users.getNumberOfElements());
        assertEquals(TEST_EMAIL, users.getContent().get(0).getEmail());
    }

}
