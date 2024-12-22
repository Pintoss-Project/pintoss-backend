package pintoss.giftmall.domains.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pintoss.giftmall.domains.user.dto.NaverUserResponse;

@Service
@RequiredArgsConstructor
public class NaverOAuthService {

    private String clientId = "ntP6_ogtZLkuD6J774IT";
    private String clientSecret = "9cIj0Lo1b_";
    private final RestTemplate restTemplate;

    public String getAccessToken(String code, String state) {
        String accessTokenUrl = "https://nid.naver.com/oauth2.0/token";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("state", state);

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 요청 본문 생성
        HttpEntity<MultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(new LinkedMultiValueMap<>(params), headers);

        String response = restTemplate.postForObject(accessTokenUrl, params, String.class);

        return extractAccessToken(response);
    }

    public NaverUserResponse getUserInfo(String accessToken) {
        String userInfoUrl = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<NaverUserResponse> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, NaverUserResponse.class);

        return response.getBody();
    }

    private String extractAccessToken(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            return rootNode.path("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract access token", e);
        }
    }

}
