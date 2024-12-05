package pintoss.giftmall.domains.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pintoss.giftmall.common.enums.PayMethod;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BillgateService {

    private final RestTemplate restTemplate;

    //결제 서비스 코드(신용카드/ 휴대폰)
    private String getServiceCode(PayMethod payMethod) {
        switch (payMethod) {
            case CARD:
                return "0900"; // 신용카드
            case PHONE:
                return "1100"; // 휴대폰
            default:
                throw new IllegalArgumentException("지원하지 않는 결제 수단입니다.");
        }
    }

    //결제 승인
    public ResponseEntity<String> approvePayment(String transactionId, BigDecimal amount, PayMethod payMethod) {
        String serviceCode = getServiceCode(payMethod);
        String url = "https://webapi.billgate.net:8443/webapi/approve.jsp";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("SERVICE_ID", "YOUR_SERVICE_ID");
        params.add("SERVICE_CODE", serviceCode);
        params.add("ORDER_ID", transactionId);
        params.add("AMOUNT", amount.toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        return restTemplate.postForEntity(url, request, String.class);
    }

    //결제 취소
    public ResponseEntity<String> cancelPayment(String transactionId, PayMethod payMethod) {
        String serviceCode = getServiceCode(payMethod);
        String url = "https://webapi.billgate.net:8443/webapi/cancel.jsp";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("SERVICE_ID", "YOUR_SERVICE_ID");
        params.add("SERVICE_CODE", serviceCode);
        params.add("ORDER_ID", transactionId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        return restTemplate.postForEntity(url, request, String.class);
    }
}
