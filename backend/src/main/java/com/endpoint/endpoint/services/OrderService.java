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

    @Autowired 
    private final BookService bookService;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, BookRepository bookRepository,BookService bookService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> ordersDTO = orders.stream()
                .map(o -> OrderMapper.toDTO(o))
                .collect(Collectors.toList());
        return ordersDTO;
    }

    public List<OrderDTO> getOrdersByUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderDTO> ordersDTO = orders.stream()
                .map(o -> OrderMapper.toDTO(o))
                .collect(Collectors.toList());
        return ordersDTO;
    }

    public OrderDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        return OrderMapper.toDTO(order);
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order orderEntity = OrderMapper.toEntity(orderDTO);
        orderEntity.setUserId(user.getId());

        List<BookAmountPair> checkedBooks = orderDTO.getBooks().stream()
                .map(pair -> {
                    String isdn = pair.getBook() != null ? pair.getBook().getIsdn() : null;
                    if (isdn == null) {
                        throw new RuntimeException("Book ISBN (isdn) cannot be null");
                    }

                    Integer amount = pair.getAmount();
                    Integer stock = pair.getBook().getStock();

                    if(amount > stock){
                       throw new RuntimeException("Book stock for ISDN : " + isdn + " is " + stock + " .Please change the requested amount"); 
                    }
                    bookService.updateBookStock(isdn, stock - amount);
                    Book freshBook = bookRepository.findById(isdn)
                            .orElseThrow(() -> new RuntimeException("Book not found: " + isdn));

                    pair.setBook(freshBook);
                    pair.setOrder(orderEntity); // to avoid null order_id
                    return pair;
                })
                .collect(Collectors.toList());

        orderEntity.setBooks(checkedBooks);
        orderRepository.save(orderEntity);

        return orderDTO;
    }

    @Transactional
    public OrderDTO updateOrderInsertBook(Integer id, Integer amount, String isdn) {
        if (orderRepository.existsById(id)) {

            // Add the new book after checking it exists
            Book newBook = bookRepository.findByIsdn(isdn);
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // go to the order and add the new pair
            Integer stock = newBook.getStock();

            if(amount > stock){
                throw new RuntimeException("Book stock for ISDN : " + isdn + " is " + stock + " .Please change the requested amount"); 
            }
            bookService.updateBookStock(isdn, stock - amount);
            order.getBooks().add(new BookAmountPair(order, newBook, amount));
            return OrderMapper.toDTO(order);
        }
        return null;
    }

    @Transactional
    public OrderDTO updateOrderChangeAmount(Integer id, Integer amount, String isdn) {
        if (!orderRepository.existsById(id)) {
            return null; // order does not exist
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        // Check if the book with the given isdn exists in the order
        boolean bookFound = order.getBooks().stream()
                .anyMatch(pair -> pair.getBook().getIsdn().equalsIgnoreCase(isdn));

        if (!bookFound) {
            throw new RuntimeException("Book with ISDN " + isdn + " not found in the order");
        }
        // find the pair and change the amount
        for (int i = 0; i < order.getBooks().size(); i++) {
            if (order.getBooks().get(i).getBook().getIsdn().equalsIgnoreCase(isdn)) {
                Integer stock = order.getBooks().get(i).getBook().getStock(); 
                if (amount > stock) {
                    throw new RuntimeException("Book stock for ISDN: " + isdn + " is " + stock +
                            ". Please change the requested amount.");
                }
                bookService.updateBookStock(isdn, stock - amount);
                order.getBooks().get(i).setAmount(amount);
                break;
            }

        }
        orderRepository.save(order);
        return OrderMapper.toDTO(order);
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
