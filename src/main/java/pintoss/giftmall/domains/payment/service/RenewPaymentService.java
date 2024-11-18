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

    // ���� ����
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

    // ���� ��û
    public Message MessageAuthProcess(Map<String,String> authInfo) throws Exception {
        String serviceId = authInfo.get("serviceId");
        String serviceCode = authInfo.get("serviceCode");
        String msg = authInfo.get("message");

        //�޽��� Length ����
        byte[] b = new byte[msg.getBytes().length - 4] ;
        System.arraycopy(msg.getBytes(), 4, b, 0, b.length);

        Message requestMsg = new Message(b, getCipher(serviceId,serviceCode)) ;

        Message responseMsg = null ;

        ServiceBroker sb = new ServiceBroker(getConfigFilePath(), serviceCode);

        responseMsg = sb.invoke(requestMsg);

        return responseMsg;
    }


    // �κ� ȯ�� ó��
    public Refund processPartialRefund(RefundRequestDTO dto) throws Exception {
        RenewPayment payment = findPaymentByTransactionId(dto.getTransactionId());

        Map<String, String> cancelInfo = new HashMap<>();
        cancelInfo.put("serviceId", payment.getServiceId());
        cancelInfo.put("serviceCode", payment.getPayMethod().name());
        cancelInfo.put("orderId", payment.getOrderId());
        cancelInfo.put("orderDate", payment.getOrderDate().toString());
        cancelInfo.put("transactionId", payment.getTransactionId());
        cancelInfo.put("command", "9010"); // �κ� ȯ�� ��ɾ�
        cancelInfo.put("cancelType", dto.getCancelType());
        cancelInfo.put("cancelAmount", dto.getCancelAmount().toString());

        Message responseMsg = partCancelProcess(cancelInfo);

        return saveRefund(dto, payment, responseMsg);
    }

    // ��ü ȯ�� ó��
    public Refund processFullRefund(RefundRequestDTO dto) throws Exception {
        RenewPayment payment = findPaymentByTransactionId(dto.getTransactionId());

        Map<String, String> cancelInfo = new HashMap<>();
        cancelInfo.put("serviceId", payment.getServiceId());
        cancelInfo.put("serviceCode", payment.getPayMethod().name());
        cancelInfo.put("orderId", payment.getOrderId());
        cancelInfo.put("orderDate", payment.getOrderDate().toString());
        cancelInfo.put("transactionId", payment.getTransactionId());
        cancelInfo.put("command", "9200"); // ��ü ȯ�� ��ɾ�

        Message responseMsg = cancelProcess(cancelInfo);

        return saveRefund(dto, payment, responseMsg);
    }

    // ȯ�� ����
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

    // ���� ������ ��ȸ
    private RenewPayment findPaymentByTransactionId(String transactionId) {
        return renewPaymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("���� �����Ͱ� �������� �ʽ��ϴ�."));
    }

    // �κ� ȯ�� ���� ���
    private Message partCancelProcess(Map<String, String> cancelInfo) throws Exception {
        return invokeServiceBroker(cancelInfo);
    }

    // ��ü ȯ�� ���� ���
    private Message cancelProcess(Map<String, String> cancelInfo) throws Exception {
        return invokeServiceBroker(cancelInfo);
    }

    // ServiceBroker ȣ�� ����
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

    // ���� ���� ���
    private String getConfigFilePath() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("config.ini").getFile());
        return file.getAbsolutePath();
    }

    // ���� ���� ��ȿ�� ����
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
