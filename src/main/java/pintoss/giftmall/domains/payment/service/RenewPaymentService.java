package pintoss.giftmall.domains.payment.service;

import com.galaxia.api.ConfigInfo;
import com.galaxia.api.ServiceBroker;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.merchant.Message;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.enums.PayStatus;
import pintoss.giftmall.domains.payment.domain.Refund;
import pintoss.giftmall.domains.payment.domain.RenewPayment;
import pintoss.giftmall.domains.payment.dto.PaymentRequestDTO;
import pintoss.giftmall.domains.payment.dto.RefundRequestDTO;
import pintoss.giftmall.domains.payment.infra.RefundRepository;
import pintoss.giftmall.domains.payment.infra.RenewPaymentRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
@AllArgsConstructor
public class RenewPaymentService {

    public static final String VERSION ="0100";

    private final RenewPaymentRepository  renewPaymentRepository;

    private final RefundRepository refundRepository;

    //결제 승인 및 저장
    @Transactional(noRollbackFor = Exception.class)
    public RenewPayment approveAndSavePayment(PaymentRequestDTO dto) throws Exception {
        // authInfo 생성
        Map<String, String> authInfo = new HashMap<>();
        authInfo.put("serviceId", dto.getServiceId());
        authInfo.put("serviceCode", dto.getServiceCode());
        authInfo.put("orderId", dto.getOrderId());
        authInfo.put("amount", dto.getAmount());

        // 결제 승인 요청
        Message responseMsg = MessageAuthProcess(authInfo);
        log.info("결제 승인 결과::"+responseMsg);

        // 승인 결과 확인
        if ("0000".equals(responseMsg.get("1002"))) {
            // 결제 승인 성공 시 RenewPayment 저장
            RenewPayment payment = RenewPayment.builder()
                    .serviceId(dto.getServiceId())
                    .orderId(dto.getOrderId())
                    .orderDate(dto.getOrderDate())
                    .transactionId(responseMsg.get("1001")) // 갤럭시아 응답에서 트랜잭션 ID 설정
                    .payMethod(dto.getPayMethod())
                    .payPrice(dto.getPayPrice())
                    .payStatus(PayStatus.COMPLETED) // 결제 승인 상태 설정
                    .build();
            return renewPaymentRepository.save(payment);
        } else {
            throw new IllegalStateException("결제 승인 실패: " + responseMsg.get("1003"));
        }
    }

    // 결제 저장
    public RenewPayment savePayment(PaymentRequestDTO dto) {
        RenewPayment payment = RenewPayment.builder()
                .serviceId(dto.getServiceId())
                .orderId(dto.getOrderId())
                .orderDate(dto.getOrderDate())
                .itemName(dto.getItemName())
                .payMethod(dto.getPayMethod())
                .payPrice(dto.getPayPrice())
                .payStatus(PayStatus.COMPLETED)
                .approvedAt(LocalDateTime.now())
                .build();
        return renewPaymentRepository.save(payment);
    }

    // 결제 승인
    public Message MessageAuthProcess(Map<String,String> authInfo) throws Exception {
        String serviceId = authInfo.get("serviceId");
        String serviceCode = authInfo.get("serviceCode");
        String msg = authInfo.get("message");
        String orderId = authInfo.get("orderId");
        String amount = authInfo.get("amount");

        // Message 객체 생성 (암호화 제외)
        Message requestMsg = new Message();
        requestMsg.put("SERVICE_ID", serviceId);
        requestMsg.put("ORDER_ID", orderId);
        requestMsg.put("AMOUNT", amount);
        requestMsg.put("SERVICE_CODE", serviceCode);

        // 갤럭시아 ServiceBroker 호출
        ServiceBroker sb = new ServiceBroker(getConfigFilePath(), serviceCode);

        return sb.invoke(requestMsg);
    }

    // 부분 환불
    public Refund processPartialRefund(RefundRequestDTO dto) throws Exception {
        RenewPayment payment = findPaymentByTransactionId(dto.getTransactionId());

        Map<String, String> cancelInfo = new HashMap<>();
        cancelInfo.put("serviceId", payment.getServiceId());
        cancelInfo.put("serviceCode", payment.getPayMethod().name());
        cancelInfo.put("orderId", payment.getOrderId());
        cancelInfo.put("orderDate", payment.getOrderDate().toString());
        cancelInfo.put("transactionId", payment.getTransactionId());
        cancelInfo.put("command", "9010"); // 부분 환불처리
        cancelInfo.put("cancelType", dto.getCancelType());
        cancelInfo.put("cancelAmount", dto.getCancelAmount().toString());

        Message responseMsg = partCancelProcess(cancelInfo);

        return saveRefund(dto, payment, responseMsg);
    }

    // 전체 환불처리
    public Refund processFullRefund(RefundRequestDTO dto) throws Exception {
        RenewPayment payment = findPaymentByTransactionId(dto.getTransactionId());

        Map<String, String> cancelInfo = new HashMap<>();
        cancelInfo.put("serviceId", payment.getServiceId());
        cancelInfo.put("serviceCode", payment.getPayMethod().name());
        cancelInfo.put("orderId", payment.getOrderId());
        cancelInfo.put("orderDate", payment.getOrderDate().toString());
        cancelInfo.put("transactionId", payment.getTransactionId());
        cancelInfo.put("command", "9200"); // 전체 환불처리.

        Message responseMsg = cancelProcess(cancelInfo);

        return saveRefund(dto, payment, responseMsg);
    }

    // 환불 저장
    private Refund saveRefund(RefundRequestDTO dto, RenewPayment payment, Message responseMsg) {
        Refund refund = Refund.builder()
                .transactionId(payment.getTransactionId())
                .cancelAmount(dto.getCancelAmount())
                .responseCode(responseMsg.get("1002"))
                .responseMessage(responseMsg.get("1003"))
                .payment(payment)
                .build();
        return refundRepository.save(refund);
    }

    //결제 정보 조회
    private RenewPayment findPaymentByTransactionId(String transactionId) {
        return renewPaymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다.."));
    }

    //부분 환불 처리
    private Message partCancelProcess(Map<String, String> cancelInfo) throws Exception {
        return invokeServiceBroker(cancelInfo);
    }

    //전체 환불 처리
    private Message cancelProcess(Map<String, String> cancelInfo) throws Exception {
        return invokeServiceBroker(cancelInfo);
    }

    // ServiceBroker 호출
    private Message invokeServiceBroker(Map<String, String> cancelInfo) throws Exception {
        String confPath = getConfigFilePath();

        Message requestMsg = new Message(
                VERSION,
                cancelInfo.get("serviceId"),
                cancelInfo.get("serviceCode"),
                cancelInfo.get("command"),
                cancelInfo.get("orderId"),
                cancelInfo.get("orderDate"),
                getCipher(cancelInfo.get("serviceId"), cancelInfo.get("serviceCode"))
        );

        if (cancelInfo.get("transactionId") != null) {
            requestMsg.put("1001", cancelInfo.get("transactionId"));
        }
        if (cancelInfo.get("cancelAmount") != null) {
            requestMsg.put("0012", cancelInfo.get("cancelAmount"));
        }
        if (cancelInfo.get("cancelType") != null) {
            requestMsg.put("0082", cancelInfo.get("cancelType"));
        }

        ServiceBroker sb = new ServiceBroker(confPath, cancelInfo.get("serviceCode"));
        return sb.invoke(requestMsg);
    }

    //설정 파일
    private String getConfigFilePath() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("config.ini").getFile());
        return file.getAbsolutePath();
    }

    //설정 파일 유효성 검증
    private GalaxiaCipher getCipher(String serviceId, String serviceCode) throws Exception {
        String confPath = getConfigFilePath();
        log.info(confPath);
        if (!new File(confPath).exists()) {
            throw new FileNotFoundException("Configuration file not found at: " + confPath);
        }
        ConfigInfo config = new ConfigInfo(confPath, serviceCode);
        log.info(config);
        GalaxiaCipher cipher = new Seed();
        cipher.setKey("QkZJRlBDRTI4T0c1OUtBMw==".getBytes());
        cipher.setIV("PRJ59Q2GHPT844TQ".getBytes());
        return cipher;
    }
}
