package com.index.converter;

import com.index.entity.Order;
import com.index.entity.nested.NestedOrder;
import com.index.model.OrderDTO;

/**
 * @author David Sapozhnik
 */
public final class OrderConverter {

    private OrderConverter() {
    }

    public static OrderDTO toOrderDTO(Order order) {
        var orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setAmountPaid(order.getAmountPaid());
        orderDTO.setStatus(order.getStatus());
        return orderDTO;
    }

    public static NestedOrder toOrder(OrderDTO orderDTO) {
        var order = new NestedOrder();
        order.setOrderId(orderDTO.getOrderId());
        order.setAmountPaid(orderDTO.getAmountPaid());
        order.setStatus(orderDTO.getStatus());
        return order;
    }
}
