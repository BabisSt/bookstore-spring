package com.endpoint.endpoint.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.model.BookAmountPair;
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
        List<BookAmountPair> checkedBooks = order.getBooks().stream()
                .map(pair -> {
                    Book freshBook = bookRepository.findById(pair.getBook().getIsdn())
                            .orElseThrow(() -> new RuntimeException("Book not found: " + pair.getBook().getIsdn()));
                    pair.setBook(freshBook); // update the book inside the pair
                    return pair;
                })
                .collect(Collectors.toList());

        order.setUser(user);
        order.setBooks(checkedBooks);
        return orderRepository.save(order);
    }

    public Order updateOrderInsertBook(Integer id, Integer amount, String isdn, Order order) {
        if (orderRepository.existsById(id)) {

            // Add the new book after checking it exists
            Book newBook = bookRepository.findByIsdn(isdn);

            // go to the order and add the new pair
            order.getBooks().add(new BookAmountPair(order, newBook, amount));
            return orderRepository.save(order);
        }
        return null;
    }

    public Order updateOrderChangeAmount(Integer id, Integer amount, String isdn, Order order) {
        if (!orderRepository.existsById(id)) {
            return null; // order does not exist
        }

        // Check if the book with the given isdn exists in the order
        boolean bookFound = order.getBooks().stream()
                .anyMatch(pair -> pair.getBook().getIsdn().equalsIgnoreCase(isdn));

        if (!bookFound) {
            throw new RuntimeException("Book with ISDN " + isdn + " not found in the order");
        }
        // find the pair and change the amount
        for (int i = 0; i < order.getBooks().size(); i++) {
            if (order.getBooks().get(i).getBook().getIsdn().equalsIgnoreCase(isdn)) {
                order.getBooks().get(i).setAmount(amount);
                break;
            }

        }

        return orderRepository.save(order);
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
