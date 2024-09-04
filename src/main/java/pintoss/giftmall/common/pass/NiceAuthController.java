package pintoss.giftmall.common.pass;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/nice")
public class NiceAuthController {

    private final NiceAuthService niceAuthService;

    public NiceAuthController(NiceAuthService niceAuthService) {
        this.niceAuthService = niceAuthService;
    }

    @PostMapping("/request")
    public Map<String, String> requestAuthToken(@RequestBody Map<String, String> request) {
        try {
            Map<String, String> cryptoInfo = niceAuthService.requestCryptoToken();
            String key = cryptoInfo.get("key");
            String iv = cryptoInfo.get("iv");
            String hmacKey = cryptoInfo.get("hmac_key");
            String tokenVersionId = cryptoInfo.get("token_version_id");
            String siteCode = cryptoInfo.get("site_code");
            String reqNo = cryptoInfo.get("req_no");

            String reqData = String.format("{\"requestno\":\"%s\",\"returnurl\":\"%s\",\"sitecode\":\"%s\",\"methodtype\":\"get\",\"popupyn\":\"Y\"}",
                    reqNo, request.get("returnurl"), siteCode);

            String encData = niceAuthService.encryptData(reqData, key, iv);
            String integrityValue = niceAuthService.generateHMAC(encData, hmacKey);

            return Map.of(
                    "token_version_id", tokenVersionId,
                    "enc_data", encData,
                    "integrity_value", integrityValue
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to process NICE authentication request", e);
        }
    }

    @PostMapping("/decrypt")
    public ResponseEntity<Map<String, String>> decryptData(@RequestBody Map<String, String> request) {
        try {
            String tokenVersionId = request.get("token_version_id");
            String encData = request.get("enc_data");

            String decryptedData = niceAuthService.decryptData(tokenVersionId, encData);
            Map<String, String> parsedData = niceAuthService.parseDecryptedData(decryptedData);

            return ResponseEntity.ok(parsedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

}
