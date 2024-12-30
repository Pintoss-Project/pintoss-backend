package pintoss.giftmall.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilterRegistration() {
        FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("EUC-KR");     // 인코딩 설정
        characterEncodingFilter.setForceEncoding(true);   // 항상 해당 인코딩 강제

        registrationBean.setFilter(characterEncodingFilter);
        registrationBean.addUrlPatterns("/api/payment/**");   // 모든 요청에 적용
        registrationBean.setOrder(1);            // 필터 적용 순서(최우선)

        return registrationBean;
    }

}