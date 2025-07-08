package com.endpoint.endpoint.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.endpoint.endpoint.dto.OrderDTO;
import com.endpoint.endpoint.services.OrderService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public OrderDTO getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<OrderDTO> getOrderByUser(@PathVariable Integer user_id) {
        return orderService.getOrdersByUser(user_id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(),
                    error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/insert/{id}/{isdn}/{amount}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public OrderDTO updateOrderInsertBook(@PathVariable("id") Integer id,
            @PathVariable("amount") Integer amount, @PathVariable("isdn") String isdn) {
        return orderService.updateOrderInsertBook(id, amount, isdn);
    }

    @PutMapping("/updateAmount/{id}/{isdn}/{amount}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public OrderDTO updateOrderChangeAmount(@PathVariable("id") Integer id,
            @PathVariable("amount") Integer amount, @PathVariable("isdn") String isdn) {
        return orderService.updateOrderChangeAmount(id, amount, isdn);
    }

    @DeleteMapping("/deleteOrder/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public boolean deleteOrder(@PathVariable Integer id) {
        return orderService.deleteOrder(id);
    }
}