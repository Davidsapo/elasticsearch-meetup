package com.index.service;

import com.index.dao.OrderDao;
import com.index.entity.Order;
import com.index.enums.SystemEventType;
import com.index.model.OrderDTO;
import com.index.model.events.OrderCreatedEvent;
import com.index.model.events.OrderUpdatedEvent;
import com.index.model.events.SystemEvent;
import com.index.model.events.UserUpdatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.index.converter.OrderConverter.toOrderDTO;
import static com.index.converter.ProductConverter.toNestedProducts;
import static com.index.converter.UserConverter.toUser;
import static com.index.enums.SystemEventType.ORDER_CREATED;
import static com.index.enums.SystemEventType.ORDER_UPDATED;
import static com.index.enums.SystemEventType.USER_UPDATED;

/**
 * Order Service.
 *
 * @author David Sapozhnik
 */
@Service
public class OrderService extends SystemEventService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Override
    public List<SystemEventType> getPrimaryEventTypes() {
        return List.of(ORDER_CREATED, ORDER_UPDATED);
    }

    @Override
    public List<SystemEventType> getAdditionalEventTypes() {
        return List.of(USER_UPDATED);
    }

    @Override
    public <T extends SystemEvent> void processSystemEvent(T systemEvent) {
        switch (systemEvent.getType()) {
            case ORDER_CREATED -> createNewOrder((OrderCreatedEvent) systemEvent);
            case ORDER_UPDATED -> updateOrder((OrderUpdatedEvent) systemEvent);
            case USER_UPDATED -> updateUser((UserUpdatedEvent) systemEvent);
            default -> throw new IllegalArgumentException("Invalid system event type.");
        }
    }

    /**
     * Gets order by id.
     *
     * @param orderId the order id
     * @return the order by id
     */
    public OrderDTO getOrderById(UUID orderId) {
        var order = orderDao.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));
        return toOrderDTO(order);
    }

    /**
     * Search orders.
     *
     * @param query the query
     * @return the list
     */
    public List<UUID> searchOrders(String query) {
        return orderDao.findAllBySearch(query)
                .stream()
                .map(Order::getOrderId)
                .toList();
    }

    /* Private methods */

    private void createNewOrder(OrderCreatedEvent orderCreatedEvent) {
        var order = new Order();
        order.setOrderId(orderCreatedEvent.getEntityId());
        order.setAmountPaid(orderCreatedEvent.getAmountPaid());
        order.setStatus(orderCreatedEvent.getStatus());
        order.setUser(toUser(userService.getUserById(orderCreatedEvent.getUserId())));
        order.getProducts().addAll(toNestedProducts(productService.findAllByIds(orderCreatedEvent.getProductIds())));
        order.setCreatedAt(orderCreatedEvent.getCreatedAt());
        orderDao.save(order);
    }

    private void updateOrder(OrderUpdatedEvent orderUpdatedEvent) {
        var order = orderDao.findById(orderUpdatedEvent.getEntityId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));
        order.setStatus(orderUpdatedEvent.getStatus());
        orderDao.save(order);
    }

    private void updateUser(UserUpdatedEvent userUpdatedEvent) {
        var orders = orderDao.findByUserId(userUpdatedEvent.getEntityId());
        orders.forEach(order -> {
            var user = order.getUser();
            user.setFullName(userUpdatedEvent.getFullName());
            user.setEmail(userUpdatedEvent.getEmail());
            user.setPhone(userUpdatedEvent.getPhone());
        });
        orderDao.saveAll(orders);
    }
}
