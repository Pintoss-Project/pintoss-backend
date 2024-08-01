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
import pintoss.giftmall.domains.order.infra.OrderRepositoryReader;
import pintoss.giftmall.domains.payment.domain.Payment;
import pintoss.giftmall.domains.payment.infra.PaymentRepository;
import pintoss.giftmall.domains.product.domain.PriceCategory;
import pintoss.giftmall.domains.product.domain.Product;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepository;
import pintoss.giftmall.domains.product.infra.PriceCategoryRepositoryReader;
import pintoss.giftmall.domains.product.infra.ProductRepository;
import pintoss.giftmall.domains.product.infra.ProductRepositoryReader;
import pintoss.giftmall.domains.user.domain.User;
import pintoss.giftmall.domains.user.infra.UserRepository;
import pintoss.giftmall.domains.user.infra.UserRepositoryReader;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderRepositoryReader orderRepositoryReader;
    private final ProductRepositoryReader productRepositoryReader;
    private final PriceCategoryRepositoryReader priceCategoryRepositoryReader;
    private final UserRepositoryReader userRepositoryReader;

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "주문내역을 찾을 수 없습니다.");
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
        userRepositoryReader.findById(userId);

        List<Order> orders = orderRepository.findAllByUserId(userId);
        if (orders.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND, "사용자의 주문내역을 찾을 수 없습니다.");
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
        return orderRepositoryReader.findById(orderId);
    }

    @Transactional
    public Long createOrder(Long userId, OrderRequest orderRequest) {
        User user = userRepositoryReader.findById(userId);

        Order order = orderRequest.toEntity(user);
        orderRepository.save(order);

        orderRequest.getOrderProducts().forEach(orderProductRequest -> {
            Product product = productRepositoryReader.findById(orderProductRequest.getProductId());
            PriceCategory priceCategory = priceCategoryRepositoryReader.findById(orderProductRequest.getPriceCategoryId());

            OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .price(orderProductRequest.getPrice())
                    .quantity(orderProductRequest.getQuantity())
                    .priceCategoryId(priceCategory.getId())
                    .build();

            orderProductRepository.save(orderProduct);
        });

        return order.getId();
    }
}
