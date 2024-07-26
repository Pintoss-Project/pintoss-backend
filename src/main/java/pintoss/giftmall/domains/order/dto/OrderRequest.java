package pintoss.giftmall.domains.order.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.user.domain.User;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotBlank
    private String orderNo;

    @NotNull
    private int orderPrice;

    @NotBlank
    private String orderStatus;

    @NotBlank
    private String payMethod;

    private boolean isSent = false;

    @Builder
    public OrderRequest(String orderNo, int orderPrice, String orderStatus, boolean isSent, String payMethod) {
        this.orderNo = orderNo;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.isSent = isSent;
        this.payMethod = payMethod;
    }

    public Order toEntity(User user) {
        return Order.builder()
                .orderNo(orderNo)
                .orderPrice(orderPrice)
                .orderStatus(orderStatus)
                .payMethod(payMethod)
                .isSent(isSent)
                .user(user)
                .build();
    }

}
