package com.purchase.controller;

import com.purchase.enums.OrderStatus;
import com.purchase.model.OrderDTO;
import com.purchase.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Order Controller.
 *
 * @author David Sapozhnik
 */
@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public OrderDTO createOrder(@RequestParam UUID userId, @RequestBody List<UUID> productIds) {
        return orderService.createOrder(userId, productIds);
    }

    @PatchMapping("/orders/{orderId}/statuses")
    public void updateOrderStatus(@PathVariable UUID orderId, @RequestParam OrderStatus orderStatus) {
        orderService.updateOrderStatus(orderId, orderStatus);
    }

    @GetMapping("/orders")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/search")
    public List<OrderDTO> searchOrders(@RequestParam(required = false) String search) {
        return orderService.searchOrders(search);
    }

    @PostMapping("users/{userId}/orders")
    public List<OrderDTO> getUserOrders(@PathVariable UUID userId) {
        return orderService.getUserOrders(userId);
    }
}
