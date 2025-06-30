package com.endpoint.endpoint.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.model.Order;
import com.endpoint.endpoint.services.OrderService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<Order> getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<List<Order>> getOrderByUser(@PathVariable Integer user_id) {
        return orderService.getOrdersByUser(user_id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> createOrder(@RequestBody @Valid Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}/{amount}/{books}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Order updateBook(@PathVariable("id") Integer id, @PathVariable("amount") Integer amount,
            @PathVariable("books") @Valid List<Book> books, @RequestBody @Valid Order order) {
        return orderService.updateOrder(id, amount, books, order);
    }

    @DeleteMapping("/deleteOrder/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public boolean deleteOrder(@PathVariable Integer id) {
        return orderService.deleteOrder(id);
    }
}
