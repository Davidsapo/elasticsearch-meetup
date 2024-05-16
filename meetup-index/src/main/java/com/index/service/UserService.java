package com.index.service;

import com.index.dao.UserDao;
import com.index.entity.User;
import com.index.enums.SystemEventType;
import com.index.enums.UserSort;
import com.index.model.UserDTO;
import com.index.model.events.OrderCreatedEvent;
import com.index.model.events.OrderUpdatedEvent;
import com.index.model.events.SystemEvent;
import com.index.model.events.UserCreatedEvent;
import com.index.model.events.UserUpdatedEvent;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.index.converter.OrderConverter.toOrder;
import static com.index.converter.UserConverter.toUserDTO;
import static com.index.enums.SystemEventType.ORDER_CREATED;
import static com.index.enums.SystemEventType.ORDER_UPDATED;
import static com.index.enums.SystemEventType.USER_CREATED;
import static com.index.enums.SystemEventType.USER_UPDATED;

/**
 * User Service.
 *
 * @author David Sapozhnik
 */
@Service
public class UserService extends SystemEventService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderService orderService;

    @Override
    public List<SystemEventType> getPrimaryEventTypes() {
        return List.of(USER_CREATED, USER_UPDATED);
    }

    @Override
    public List<SystemEventType> getAdditionalEventTypes() {
        return List.of(ORDER_CREATED, ORDER_UPDATED);
    }

    @Override
    public <T extends SystemEvent> void processSystemEvent(T systemEvent) {
        switch (systemEvent.getType()) {
            case USER_CREATED -> createNewUser((UserCreatedEvent) systemEvent);
            case USER_UPDATED -> updateUser((UserUpdatedEvent) systemEvent);
            case ORDER_CREATED -> addOrderToUser((OrderCreatedEvent) systemEvent);
            case ORDER_UPDATED -> updateOrder((OrderUpdatedEvent) systemEvent);
            default -> throw new IllegalArgumentException("Invalid system event type.");
        }
    }

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     */
    public UserDTO getUserById(UUID userId) {
        var user = userDao.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        return toUserDTO(user);
    }

    /**
     * Search users list.
     *
     * @param query the query
     * @return the list
     */
    public List<UUID> searchUsers(String query, UserSort sort, SortOrder sortOrder) {
        return userDao.findAllBySearch(query, sort, sortOrder)
                .stream()
                .map(User::getUserId)
                .toList();
    }

    /* Private methods */

    private void createNewUser(UserCreatedEvent userCreatedEvent) {
        var user = new User();
        user.setUserId(userCreatedEvent.getEntityId());
        user.setFullName(userCreatedEvent.getFullName());
        user.setEmail(userCreatedEvent.getEmail());
        user.setPhone(userCreatedEvent.getPhone());
        user.setCreatedAt(userCreatedEvent.getCreatedAt());
        userDao.save(user);
    }

    private void updateUser(UserUpdatedEvent userUpdatedEvent) {
        var user = userDao.findById(userUpdatedEvent.getEntityId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setFullName(userUpdatedEvent.getFullName());
        user.setEmail(userUpdatedEvent.getEmail());
        user.setPhone(userUpdatedEvent.getPhone());
        userDao.save(user);
    }

    private void addOrderToUser(OrderCreatedEvent orderCreatedEvent) {
        var user = userDao.findById(orderCreatedEvent.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.getOrders().add(toOrder(orderService.getOrderById(orderCreatedEvent.getEntityId())));
        userDao.save(user);
    }

    private void updateOrder(OrderUpdatedEvent orderUpdatedEvent) {
        var user = userDao.findById(orderUpdatedEvent.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        var order = user.getOrders().stream()
                .filter(o -> o.getOrderId().equals(orderUpdatedEvent.getEntityId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));
        order.setStatus(orderUpdatedEvent.getStatus());
        userDao.save(user);
    }
}
