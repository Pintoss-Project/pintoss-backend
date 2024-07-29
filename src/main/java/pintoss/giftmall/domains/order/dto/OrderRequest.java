package pintoss.giftmall.domains.order.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.user.domain.User;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequest {

    @NotBlank
    private String orderNo;

    @NotNull
    private int orderPrice;

    @NotNull
    private int discountPrice;

    @NotBlank
    private String orderStatus;

    @NotBlank
    private String payMethod;

    @NotNull
    private boolean isSent = false;

    @NotNull
    private boolean isOrderInCart = false;

    @NotNull
    private List<OrderProductRequest> orderProducts;

    @Builder
    public OrderRequest(String orderNo, int orderPrice, String orderStatus, boolean isSent, boolean isOrderInCart, String payMethod, List<OrderProductRequest> orderProducts) {
        this.orderNo = orderNo;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.payMethod = payMethod;
        this.isSent = isSent;
        this.isOrderInCart = isOrderInCart;
        this.orderProducts = orderProducts;
    }

    public Order toEntity(User user) {
        return Order.builder()
                .orderNo(orderNo)
                .orderPrice(orderPrice)
                .orderStatus(orderStatus)
                .payMethod(payMethod)
                .isSent(isSent)
                .isOrderInCart(isOrderInCart)
                .user(user)
                .build();
    }

}
