package pintoss.giftmall.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import pintoss.giftmall.common.oauth.*;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(HttpBasicConfigurer::disable)
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
                    config.setExposedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
                    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    config.setAllowedOriginPatterns(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(CsrfConfigurer::disable)
                .logout(logout -> logout
                        .logoutSuccessUrl("/api/user/login")
                        .invalidateHttpSession(true)
                )
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())

                // oauth2 설정
                .oauth2Login(oauth ->
                        oauth.userInfoEndpoint(c -> c.userService(oAuth2UserService))
                                .successHandler(oAuth2SuccessHandler)
                )

                // jwt 관련 설정
                .addFilterBefore(tokenAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass())

                // 인증 예외 핸들링
                .exceptionHandling((exceptions) -> exceptions
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler()))

                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}