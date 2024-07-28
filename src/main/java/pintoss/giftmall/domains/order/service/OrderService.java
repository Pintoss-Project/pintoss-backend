package pintoss.giftmall.domains.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pintoss.giftmall.domains.cart.infra.CartRepository;
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
        return orderRepository.findAll().stream()
                .map(order -> {
                    Payment payment = paymentRepository.findByOrderId(order.getId());
                    return OrderResponse.fromEntity(order);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(order -> {
                    Payment payment = paymentRepository.findByOrderId(order.getId());
                    return OrderResponse.fromEntity(order);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }

    @Transactional
    public Long createOrder(Long userId, OrderRequest orderRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Order order = orderRequest.toEntity(user);
        orderRepository.save(order);

        orderRequest.getOrderProducts().forEach(orderProductRequest -> {
            Product product = productRepository.findById(orderProductRequest.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
            PriceCategory priceCategory = priceCategoryRepository.findById(orderProductRequest.getPriceCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("가격 카테고리를 찾을 수 없습니다."));

            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .price(orderProductRequest.getPrice())
                    .quantity(orderProductRequest.getQuantity())
                    .priceCategory(priceCategory)
                    .build();

            orderProductRepository.save(orderProduct);
        });

        return order.getId();
    }

}
