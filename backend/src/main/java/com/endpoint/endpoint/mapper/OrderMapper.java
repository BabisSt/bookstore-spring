package com.endpoint.endpoint.mapper;

import com.endpoint.endpoint.dto.OrderDTO;
import com.endpoint.endpoint.model.Order;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderDTO(order.getUserId(), order.getBooks());
    }

    public static Order toEntity(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }

        return new Order(orderDTO.getUserId(), orderDTO.getBooks());
    }
}
