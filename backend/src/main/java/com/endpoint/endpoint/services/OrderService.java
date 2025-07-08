package com.endpoint.endpoint.services;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.endpoint.endpoint.dto.OrderDTO;
import com.endpoint.endpoint.mapper.OrderMapper;
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

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> ordersDTO = orders.stream()
                .map(o -> OrderMapper.toDTO(o))
                .collect(Collectors.toList());
        return ordersDTO;
    }

    // public List<OrderDTO> getOrdersByUser(Integer userId) {
    //     User user = userRepository.findById(userId)
    //             .orElseThrow(() -> new RuntimeException("User not found"));
    //     List<Order> orders = orderRepository.findByUser(user);
    //     List<OrderDTO> ordersDTO = orders.stream()
    //             .map(o -> OrderMapper.toDTO(o))
    //             .collect(Collectors.toList());
    //     return ordersDTO;
    // }

    // public OrderDTO getOrderById(Integer id) {
    //     Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

    //     return OrderMapper.toDTO(order);
    // }

    // public OrderDTO createOrder(OrderDTO orderDTO) {
    //     User user = userRepository.findById(orderDTO.getUser().getId())
    //             .orElseThrow(() -> new RuntimeException("User not found"));

    //     Order orderEntity = OrderMapper.toEntity(orderDTO);
    //     orderEntity.setUser(user);

    //     List<BookAmountPair> checkedBooks = orderDTO.getBooks().stream()
    //             .map(pair -> {
    //                 String isdn = pair.getBook() != null ? pair.getBook().getIsdn() : null;
    //                 if (isdn == null) {
    //                     throw new RuntimeException("Book ISBN (isdn) cannot be null");
    //                 }

    //                 Book freshBook = bookRepository.findById(isdn)
    //                         .orElseThrow(() -> new RuntimeException("Book not found: " + isdn));

    //                 pair.setBook(freshBook);
    //                 pair.setOrder(orderEntity); // to avoid null order_id
    //                 return pair;
    //             })
    //             .collect(Collectors.toList());

    //     orderEntity.setBooks(checkedBooks);
    //     orderRepository.save(orderEntity);

    //     return orderDTO;
    // }

    // public OrderDTO updateOrderInsertBook(Integer id, Integer amount, String isdn) {
    //     if (orderRepository.existsById(id)) {

    //         // Add the new book after checking it exists
    //         Book newBook = bookRepository.findByIsdn(isdn);
    //         Order order = orderRepository.findById(id)
    //                 .orElseThrow(() -> new RuntimeException("Order not found"));

    //         // go to the order and add the new pair
    //         order.getBooks().add(new BookAmountPair(order, newBook, amount));
    //         return OrderMapper.toDTO(order);
    //     }
    //     return null;
    // }

    // public OrderDTO updateOrderChangeAmount(Integer id, Integer amount, String isdn) {
    //     if (!orderRepository.existsById(id)) {
    //         return null; // order does not exist
    //     }
    //     Order order = orderRepository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("Order not found"));
    //     // Check if the book with the given isdn exists in the order
    //     boolean bookFound = order.getBooks().stream()
    //             .anyMatch(pair -> pair.getBook().getIsdn().equalsIgnoreCase(isdn));

    //     if (!bookFound) {
    //         throw new RuntimeException("Book with ISDN " + isdn + " not found in the order");
    //     }
    //     // find the pair and change the amount
    //     for (int i = 0; i < order.getBooks().size(); i++) {
    //         if (order.getBooks().get(i).getBook().getIsdn().equalsIgnoreCase(isdn)) {
    //             order.getBooks().get(i).setAmount(amount);
    //             break;
    //         }

    //     }
    //     orderRepository.save(order);
    //     return OrderMapper.toDTO(order);
    // }

    // @Transactional // Without the @Transactional annotation, JPA doesn’t open a transaction and
    //                // therefore cannot perform write operations like remove() or delete.
    // public boolean deleteOrder(Integer id) {
    //     if (orderRepository.existsById(id)) {
    //         orderRepository.deleteById(id);
    //         return true;
    //     }
    //     return false;
    // }

}
