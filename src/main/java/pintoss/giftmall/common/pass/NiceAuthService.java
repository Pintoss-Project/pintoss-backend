package pintoss.giftmall.common.pass;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@Service
public class NiceAuthService {

    private String clientId = "f93f0663-aacc-409b-a7e9-0577d4c42cea";
    private String productId = "2101979031";
    private String apiUrl = "https://svc.niceapi.co.kr:22001";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();  // ObjectMapper instance

    private final Map<String, Map<String, String>> keyStorage = new HashMap<>();

    public Map<String, String> requestCryptoToken() throws Exception {
        try {
            String accessToken = "1b7a33cb-6c41-4a54-b583-5db46d0983ba";
            long currentTimestamp = new Date().getTime() / 1000;
            String authHeader = Base64.getEncoder().encodeToString((accessToken + ":" + currentTimestamp + ":" + clientId).getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "bearer " + authHeader);
            headers.set("ProductID", productId);

            String req_dtim = new Date().toInstant().toString().substring(0, 19).replaceAll("[:T-]", "");
            String req_no = "REQ" + req_dtim + UUID.randomUUID().toString().replace("-", "").substring(0, 4);

            Map<String, Object> dataBody = new HashMap<>();
            dataBody.put("req_dtim", req_dtim);
            dataBody.put("req_no", req_no);
            dataBody.put("enc_mode", "1");

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("dataHeader", Map.of("CNTY_CD", "ko"));
            requestBody.put("dataBody", dataBody);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    apiUrl + "/digital/niceid/api/v1.0/common/crypto/token",
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody().get("dataBody");
                String siteCode = (String) responseBody.get("site_code");
                String tokenVersionId = (String) responseBody.get("token_version_id");
                String tokenVal = (String) responseBody.get("token_val");

                String result = req_dtim.trim() + req_no.trim() + tokenVal.trim();
                String resultVal = encryptSHA256(result);

                String key = resultVal.substring(0, 16);
                String iv = resultVal.substring(resultVal.length() - 16);
                String hmacKey = resultVal.substring(0, 32);

                Map<String, String> cryptoInfo = new HashMap<>();
                cryptoInfo.put("token_version_id", tokenVersionId);
                cryptoInfo.put("key", key);
                cryptoInfo.put("iv", iv);
                cryptoInfo.put("hmac_key", hmacKey);
                cryptoInfo.put("site_code", siteCode);
                cryptoInfo.put("req_no", req_no);

                keyStorage.put(tokenVersionId, cryptoInfo); // Store encryption keys for decryption

                return cryptoInfo;
            } else {
                throw new RuntimeException("Failed to request crypto token from NICE API. Status Code: " + responseEntity.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while requesting crypto token: " + e.getMessage(), e);
        }
    }

    public String decryptData(String tokenVersionId, String encData) throws Exception {
        Map<String, String> cryptoInfo = keyStorage.get(tokenVersionId);
        if (cryptoInfo == null) {
            throw new RuntimeException("No encryption information found for the provided token.");
        }

        String key = cryptoInfo.get("key");
        System.out.println("key" + key);
        String iv = cryptoInfo.get("iv");
        System.out.println("iv" + iv);

        SecretKey secureKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encData));
        System.out.println("decryptedBytes" + Arrays.toString(decryptedBytes));
        return new String(decryptedBytes, "euc-kr");
    }

    public Map<String, String> parseDecryptedData(String decryptedData) throws JsonProcessingException {
        // Assuming the decryptedData is a JSON string. If it's not, you'll need to adjust this logic accordingly.
        return objectMapper.readValue(decryptedData, new TypeReference<Map<String, String>>() {});
    }

    // Other methods like encryptData, generateHMAC, encryptSHA256
    public String encryptData(String data, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String generateHMAC(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmac.init(secretKey);
        return Base64.getEncoder().encodeToString(hmac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    private String encryptSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}
