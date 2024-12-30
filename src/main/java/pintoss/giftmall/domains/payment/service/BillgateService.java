package pintoss.giftmall.domains.payment.service;

import lombok.AllArgsConstructor;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class BillgateService {

    private final RestTemplate restTemplate;

    private static final String SERVICE_ID = "M2483583"; // 실제 서비스 ID로 설정
    private static final String BASE_URL = "https://webapi.billgate.net:8443/webapi/";

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

    // 공통 요청 헤더 생성
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    // 공통 요청 파라미터 추가
    private MultiValueMap<String, String> createCommonParams(String transactionId, BigDecimal amount, String serviceCode) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("SERVICE_ID", SERVICE_ID);
        params.add("SERVICE_CODE", serviceCode);
        params.add("ORDER_ID", transactionId);
        params.add("AMOUNT", amount.toString());
        params.add("ORDER_DATE", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        return params;
    }

    //결제 승인
    public ResponseEntity<String> approvePayment(String transactionId, BigDecimal amount, PayMethod payMethod, String payMessage) {
        String serviceCode = getServiceCode(payMethod);
        String url =  BASE_URL + "approve.jsp";

        MultiValueMap<String, String> params = createCommonParams(transactionId, amount, serviceCode);
        params.add("PAY_MESSAGE",payMessage); // PAY_MESSAGE 추가.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, createHeaders());
        System.out.println("Approve Payment Request: " + params);
        return restTemplate.postForEntity(url, request, String.class);
    }

    //결제 취소
    public ResponseEntity<String> cancelPayment(String transactionId, PayMethod payMethod) {
        String serviceCode = getServiceCode(payMethod);
        String url = BASE_URL + "cancel.jsp";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("SERVICE_ID", SERVICE_ID);
        params.add("SERVICE_CODE", serviceCode);
        params.add("ORDER_ID", transactionId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, createHeaders());
        System.out.println("Cancel Payment Request: " + params);
        return restTemplate.postForEntity(url, request, String.class);
    }

    // 부분 취소
    public ResponseEntity<String> partialCancelPayment(String transactionId, BigDecimal cancelAmount, PayMethod payMethod, String cancelReason) {
        String serviceCode = getServiceCode(payMethod);
        String url = BASE_URL + "partialCancel.jsp";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("SERVICE_ID", SERVICE_ID);
        params.add("SERVICE_CODE", serviceCode);
        params.add("ORDER_ID", transactionId);
        params.add("CANCEL_AMOUNT", cancelAmount.toString());
        params.add("CANCEL_REASON", cancelReason != null ? cancelReason : "부분 취소");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, createHeaders());

        System.out.println("Partial Cancel Payment Request: " + params);

        return restTemplate.postForEntity(url, request, String.class);
    }
}
