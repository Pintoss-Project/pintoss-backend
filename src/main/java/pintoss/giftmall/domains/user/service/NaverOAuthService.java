package pintoss.giftmall.domains.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pintoss.giftmall.domains.user.dto.NaverUserResponse;

import java.util.HashMap;
import java.util.Map;

@Service
public class NaverOAuthService {

    private String clientId = "ntP6_ogtZLkuD6J774IT";
    private String clientSecret = "9cIj0Lo1b_";
    private final RestTemplate restTemplate = new RestTemplate();

    public String getAccessToken(String code, String state) {
        String accessTokenUrl = "https://nid.naver.com/oauth2.0/token";
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("code", code);
        params.put("state", state);

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
