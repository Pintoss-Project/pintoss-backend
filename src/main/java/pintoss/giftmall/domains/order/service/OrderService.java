package pintoss.giftmall.domains.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.common.exceptions.CustomException;
import pintoss.giftmall.common.exceptions.ErrorCode;
import pintoss.giftmall.domains.order.domain.Order;
import pintoss.giftmall.domains.order.domain.OrderProduct;
import pintoss.giftmall.domains.order.dto.OrderRequest;
import pintoss.giftmall.domains.order.dto.OrderResponse;
import pintoss.giftmall.domains.order.infra.OrderProductRepository;
import pintoss.giftmall.domains.order.infra.OrderRepository;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.payment.infra.PaymentRepository;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepository;
import pintoss.giftmall.domains.product.infra.ProductRepository;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final PriceCategoryRepository priceCategoryRepository;

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "주문내역을 다시 확인해주세요.");
        }
        return orders.stream()
                .map(order -> {
                    Payment payment = paymentRepository.findByOrderId(order.getId());
                    return OrderResponse.fromEntity(order);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAllByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "사용자 id를 다시 확인해주세요."));

        List<Order> orders = orderRepository.findAllByUserId(userId);
        if (orders.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "사용자의 주문을 찾을 수 없습니다.");
        }
        return orders.stream()
                .map(order -> {
                    Payment payment = paymentRepository.findByOrderId(order.getId());
                    return OrderResponse.fromEntity(order);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "주문 id를 다시 확인해주세요."));
    }

    @Transactional
    public Long createOrder(Long userId, OrderRequest orderRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "유저 id를 다시 확인해주세요."));

        Order order = orderRequest.toEntity(user);
        orderRepository.save(order);

        orderRequest.getOrderProducts().forEach(orderProductRequest -> {
            Product product = productRepository.findById(orderProductRequest.getProductId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "상품 id를 다시 확인해주세요."));
            PriceCategory priceCategory = priceCategoryRepository.findById(orderProductRequest.getPriceCategoryId())
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "가격 카테고리 id를 다시 확인해주세요."));

            try {
                OrderProduct orderProduct = OrderProduct.builder()
                        .order(order)
                        .product(product)
                        .price(orderProductRequest.getPrice())
                        .quantity(orderProductRequest.getQuantity())
                        .priceCategoryId(priceCategory.getId())
                        .build();

                orderProductRepository.save(orderProduct);
            } catch (Exception e) {
                throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "주문서", ErrorCode.CREATION_FAILURE);
            }
        });

        return order.getId();
    }
}
