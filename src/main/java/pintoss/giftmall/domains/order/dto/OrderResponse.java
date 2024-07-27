package pintoss.giftmall.domains.order.dto;

import lombok.Builder;
import lombok.Getter;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.payment.domain.Payment;

@Getter
public class OrderResponse {

    private Long id;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String orderNo;
    private int orderPrice;
    private String orderStatus;
    private String payMethod;
    private boolean isSent;
    private String payStatus;

    @Builder
    public OrderResponse(Long id, String userName, String userEmail, String userPhone, String orderNo, int orderPrice, String orderStatus, boolean isSent, String payMethod, String payStatus) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.orderNo = orderNo;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.payMethod = payMethod;
        this.isSent = isSent;
        this.payStatus = payStatus;
    }

    public static OrderResponse fromEntity(Order order, Payment payment) {
        return OrderResponse.builder()
                .id(order.getId())
                .userName(order.getUser().getName())
                .userEmail(order.getUser().getEmail())
                .userPhone(order.getUser().getPhone())
                .orderNo(order.getOrderNo())
                .orderPrice(order.getOrderPrice())
                .orderStatus(order.getOrderStatus())
                .isSent(order.isSent())
                .payMethod(order.getPayMethod())
                .payStatus(payment != null ? payment.getPayStatus() : null)
                .build();
    }

}
