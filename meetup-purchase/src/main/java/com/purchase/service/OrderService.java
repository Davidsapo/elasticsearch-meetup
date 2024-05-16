package com.purchase.service;

import com.purchase.client.AuthClient;
import com.purchase.client.ContentClient;
import com.purchase.client.IndexClient;
import com.purchase.entity.Order;
import com.purchase.enums.OrderStatus;
import com.purchase.model.OrderDTO;
import com.purchase.model.ProductDTO;
import com.purchase.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.purchase.enums.OrderStatus.PAID;
import static java.util.Comparator.comparingInt;
import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * Order Service.
 *
 * @author David Sapozhnik
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ContentClient contentClient;

    @Autowired
    private AuthClient authClient;

    @Autowired
    private IndexClient indexClient;

    /**
     * Create order dto.
     *
     * @param userId     the user id
     * @param productIds the product ids
     * @return the order dto
     */
    @Transactional
    public OrderDTO createOrder(UUID userId, List<UUID> productIds) {
        var order = new Order();
        order.setUserId(userId);
        order.setProductIds(productIds);
        order.setStatus(PAID);
        var products = contentClient.getProducts(productIds);
        order.setAmountPaid(products.stream().mapToDouble(ProductDTO::getPrice).sum());
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);
        var orderDTO = new OrderDTO();
        copyProperties(order, orderDTO);
        orderDTO.getProducts().addAll(products);
        orderDTO.setUser(authClient.getUser(orderDTO.getUserId()));
        return orderDTO;
    }

    /**
     * Update order status.
     *
     * @param orderId the order id
     * @param status  the status
     */
    @Transactional
    public void updateOrderStatus(UUID orderId, OrderStatus status) {
        var order = orderRepository.getReferenceById(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }

    /**
     * Gets all orders.
     *
     * @return the all orders
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    var orderDTO = new OrderDTO();
                    copyProperties(order, orderDTO);
                    orderDTO.getProducts().addAll(contentClient.getProducts(order.getProductIds()));
                    orderDTO.setUser(authClient.getUser(order.getUserId()));
                    return orderDTO;
                })
                .toList();
    }

    /**
     * Gets user orders.
     *
     * @return the all orders
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> getUserOrders(UUID userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(order -> {
                    var orderDTO = new OrderDTO();
                    copyProperties(order, orderDTO);
                    return orderDTO;
                })
                .toList();
    }

    /**
     * Search users list.
     *
     * @return the all users
     */
    @Transactional(readOnly = true)
    public List<OrderDTO> searchOrders(String search) {

        var orderIds = indexClient.search(search);

        var orders = orderRepository.findAllById(orderIds).stream()
                .map(order -> {
                    var orderDTO = new OrderDTO();
                    copyProperties(order, orderDTO);
                    orderDTO.getProducts().addAll(contentClient.getProducts(order.getProductIds()));
                    orderDTO.setUser(authClient.getUser(order.getUserId()));
                    return orderDTO;
                })
                .toList();

        return orders.stream().sorted(comparingInt(order -> orderIds.indexOf(order.getOrderId()))).toList();
    }
}
