package com.endpoint.endpoint.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.model.Order;
import com.endpoint.endpoint.model.User;
import com.endpoint.endpoint.repositories.BookRepository;
import com.endpoint.endpoint.repositories.OrderRepository;
import com.endpoint.endpoint.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BookRepository bookRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<List<Order>> getOrdersByUser(Integer userId) {
        return userRepository.findById(userId)
                .flatMap(user -> orderRepository.findByUser(user));
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        User user = userRepository.findById(order.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Book> books = order.getBooks().stream()
                .map(book -> bookRepository.findById(book.getIsdn())
                        .orElseThrow(() -> new RuntimeException("Book not found: " + book.getIsdn())))
                .collect(Collectors.toList());

        order.setUser(user);
        order.setBooks(books);

        return orderRepository.save(order);
    }

    public Order updateOrderBooks(Integer id, List<Book> books, Order order) {
        if (orderRepository.existsById(id)) {
            order.setBooks(books);
            return orderRepository.save(order);
        }
        return null;
    }

    //i need to find the specific book and then change its amount
    public Order updateOrderAmount(Integer id,Integer amount, Book book, Order order) {
        if (orderRepository.existsById(id) && orderRepository.) {
            order.setBooks(books);
            return orderRepository.save(order);
        }
        return null;
    }

    @Transactional // Without the @Transactional annotation, JPA doesn’t open a transaction and
                   // therefore cannot perform write operations like remove() or delete.
    public boolean deleteOrder(Integer id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
