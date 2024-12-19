package pintoss.giftmall.domains;

import com.galaxia.api.ConfigInfo;
import com.galaxia.api.ServiceBroker;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.merchant.Message;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class TestPaymentService {

    public static final String VERSION ="0100";

    private final RenewPaymentRepository renewPaymentRepository;

    private final RefundRepository refundRepository;

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

    @Transactional
    public RenewPayment saveRenewPayment(PaymentRequestDTO dto) {
        RenewPayment renewPayment = RenewPayment
                .builder()
                .itemName(dto.getItemName())
                .serviceId(dto.getServiceId())
                .orderId(dto.getOrderId())
                .orderDate(dto.getOrderDate())
                .payMethod(dto.getPayMethod())
                .payPrice(dto.getPayPrice())
                .payStatus(dto.getPayStatus())
                .build();
        return renewPaymentRepository.save(renewPayment);
    }

    //환불 저장
    @Transactional(noRollbackFor = IllegalArgumentException.class)
    public Refund saveRefund(RefundRequestDTO dto, RenewPayment payment) {
        Refund refund = Refund.builder()
                .transactionId(dto.getTransactionId())
                .cancelAmount(dto.getCancelAmount())
                .responseCode(dto.getResponseCode())
                .responseMessage(dto.getResponseMessage())
                .payment(payment)
                .build();
        return refundRepository.save(refund);
    }

    //설정 파일을 통해 key, iv값 가져옴
    private GalaxiaCipher getCipher(String serviceId, String serviceCode) throws Exception {
        String confPath = getConfigFilePath();
        GalaxiaCipher cipher = null ;

        String key = null ;
        String iv = null ;

        if (!isFileValid(confPath)) {
            throw new FileNotFoundException("Configuration file not found at: " + confPath);
        }

        try {
            ConfigInfo config = new ConfigInfo(confPath, serviceCode);
            key = config.getKey();
            iv = config.getIv();
            log.info("ky??"+key);
            log.info("iv???"+iv);
            cipher = new Seed();
            cipher.setKey(key.getBytes());
            cipher.setIV(iv.getBytes());
        } catch(Exception e) {
            throw e ;
        }
        return cipher;
    }

    //환불 기능
    public Refund processCancel(RefundRequestDTO dto) throws Exception {
        RenewPayment payment = renewPaymentRepository.findByTransactionId(dto.getTransactionId())
                .orElseThrow(() -> new IllegalArgumentException("결제 데이터가 존재하지 않습니다."));

        Map<String, String> cancelInfo = new HashMap<>();
        cancelInfo.put("serviceId", payment.getServiceId());
        cancelInfo.put("serviceCode", payment.getPayMethod().name());
        cancelInfo.put("orderId", payment.getOrderId());
        cancelInfo.put("orderDate", payment.getOrderDate().toString());
        cancelInfo.put("transactionId", payment.getTransactionId());
        cancelInfo.put("command", dto.getCancelAmount() != null ? "9010" : "9200");

        if (dto.getCancelAmount() != null) {
            cancelInfo.put("cancelType", dto.getResponseCode());
            cancelInfo.put("cancelAmount", dto.getCancelAmount().toString());
        }

        Message responseMsg = dto.getCancelAmount() != null
                ? partCancelProcess(cancelInfo)
                : cancelProcess(cancelInfo);

        Refund refund = saveRefund(dto, payment);
        refund.setResponseCode(responseMsg.get("1002"));
        refund.setResponseMessage(responseMsg.get("1003"));
        refund.setCancelAmount(dto.getCancelAmount());

        return refundRepository.save(refund);
    }

    private Message cancelProcess(Map<String, String> cancelInfo) throws Exception {
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

        ServiceBroker sb = new ServiceBroker(confPath, cancelInfo.get("serviceCode"));
        return sb.invoke(requestMsg);
    }

    private Message partCancelProcess(Map<String, String> cancelInfo) throws Exception {
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

    //config.ini 경로
    private String getConfigFilePath() throws Exception {
        // resources 폴더 내 config.ini의 절대 경로를 가져옴
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("config.ini").getFile());
        return file.getAbsolutePath();
    }

    //설정파일 유효성
    private boolean isFileValid(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.canRead()) {
            log.error("Invalid file path or file cannot be read: {}", filePath);
            return false;
        }
        return true;
    }
}
