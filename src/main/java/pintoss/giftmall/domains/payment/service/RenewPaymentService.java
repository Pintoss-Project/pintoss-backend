package pintoss.giftmall.domains.payment.service;

import com.galaxia.api.ConfigInfo;
import com.galaxia.api.ServiceBroker;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.merchant.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pintoss.giftmall.domains.payment.domain.Refund;
import pintoss.giftmall.domains.payment.domain.RenewPayment;
import pintoss.giftmall.domains.payment.dto.PaymentRequestDTO;
import pintoss.giftmall.domains.payment.dto.RefundRequestDTO;
import pintoss.giftmall.domains.payment.infra.RefundRepository;
import pintoss.giftmall.domains.payment.infra.RenewPaymentRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class RenewPaymentService {

    public static final String VERSION ="0100";

    private final RenewPaymentRepository  renewPaymentRepository;

    private final RefundRepository refundRepository;

    // 결제 저장
    public RenewPayment savePayment(PaymentRequestDTO dto) {
        RenewPayment payment = RenewPayment.builder()
                .serviceId(dto.getServiceId())
                .orderId(dto.getOrderId())
                .orderDate(dto.getOrderDate())
                .transactionId(dto.getTransactionId())
                .payMethod(dto.getPayMethod())
                .payPrice(dto.getPayPrice())
                .payStatus(dto.getPayStatus())
                .build();
        return renewPaymentRepository.save(payment);
    }

    // 승인 요청
    public Message MessageAuthProcess(Map<String,String> authInfo) throws Exception {
        String serviceId = authInfo.get("serviceId");
        String serviceCode = authInfo.get("serviceCode");
        String msg = authInfo.get("message");

        //메시지 Length 제거
        byte[] b = new byte[msg.getBytes().length - 4] ;
        System.arraycopy(msg.getBytes(), 4, b, 0, b.length);

        Message requestMsg = new Message(b, getCipher(serviceId,serviceCode)) ;

        Message responseMsg = null ;

        ServiceBroker sb = new ServiceBroker(getConfigFilePath(), serviceCode);

        responseMsg = sb.invoke(requestMsg);

        return responseMsg;
    }


    // 부분 환불 처리
    public Refund processPartialRefund(RefundRequestDTO dto) throws Exception {
        RenewPayment payment = findPaymentByTransactionId(dto.getTransactionId());

        Map<String, String> cancelInfo = new HashMap<>();
        cancelInfo.put("serviceId", payment.getServiceId());
        cancelInfo.put("serviceCode", payment.getPayMethod().name());
        cancelInfo.put("orderId", payment.getOrderId());
        cancelInfo.put("orderDate", payment.getOrderDate().toString());
        cancelInfo.put("transactionId", payment.getTransactionId());
        cancelInfo.put("command", "9010"); // 부분 환불 명령어
        cancelInfo.put("cancelType", dto.getCancelType());
        cancelInfo.put("cancelAmount", dto.getCancelAmount().toString());

        Message responseMsg = partCancelProcess(cancelInfo);

        return saveRefund(dto, payment, responseMsg);
    }

    // 전체 환불 처리
    public Refund processFullRefund(RefundRequestDTO dto) throws Exception {
        RenewPayment payment = findPaymentByTransactionId(dto.getTransactionId());

        Map<String, String> cancelInfo = new HashMap<>();
        cancelInfo.put("serviceId", payment.getServiceId());
        cancelInfo.put("serviceCode", payment.getPayMethod().name());
        cancelInfo.put("orderId", payment.getOrderId());
        cancelInfo.put("orderDate", payment.getOrderDate().toString());
        cancelInfo.put("transactionId", payment.getTransactionId());
        cancelInfo.put("command", "9200"); // 전체 환불 명령어

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

    // 결제 데이터 조회
    private RenewPayment findPaymentByTransactionId(String transactionId) {
        return renewPaymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("결제 데이터가 존재하지 않습니다."));
    }

    // 부분 환불 전문 통신
    private Message partCancelProcess(Map<String, String> cancelInfo) throws Exception {
        return invokeServiceBroker(cancelInfo);
    }

    // 전체 환불 전문 통신
    private Message cancelProcess(Map<String, String> cancelInfo) throws Exception {
        return invokeServiceBroker(cancelInfo);
    }

    // ServiceBroker 호출 로직
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

    // 설정 파일 경로
    private String getConfigFilePath() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("config.ini").getFile());
        return file.getAbsolutePath();
    }

    // 설정 파일 유효성 검증
    private GalaxiaCipher getCipher(String serviceId, String serviceCode) throws Exception {
        String confPath = getConfigFilePath();
        if (!new File(confPath).exists()) {
            throw new FileNotFoundException("Configuration file not found at: " + confPath);
        }
        ConfigInfo config = new ConfigInfo(confPath, serviceCode);
        GalaxiaCipher cipher = new Seed();
        cipher.setKey(config.getKey().getBytes());
        cipher.setIV(config.getIv().getBytes());
        return cipher;
    }
}
