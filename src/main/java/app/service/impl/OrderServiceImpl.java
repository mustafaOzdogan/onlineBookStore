package app.service.impl;

import app.api.request.CreateOrderRequest;
import app.api.request.GetOrderInIntervalRequest;
import app.api.response.BaseApiResponse;
import app.domain.BookStock;
import app.domain.Order;
import app.dto.BookDTO;
import app.dto.OrderDTO;
import app.dto.enums.OrderStatus;
import app.repository.OrderRepository;
import app.service.BookService;
import app.service.BookStockService;
import app.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService
{
    private BookService bookService;
    private OrderRepository orderRepository;
    private BookStockService bookStockService;

    @Override
    public BaseApiResponse createOrder(CreateOrderRequest request)
    {
        BaseApiResponse response = new BaseApiResponse.Builder().build();
        Map<String, String> responseMessages = new HashMap<>();

        request.getBooks().stream().forEach(bookOrder ->
        {
            try
            {
                BookDTO book = bookService.getBookIfExist(bookOrder.getBookId());

                reduceBookStockIfExist(bookOrder.getBookId(), bookOrder.getNumberOfBookOrdered());
                createOrderDocument(bookOrder.getBookId(), request.getCustomerId());

                responseMessages.put(bookOrder.getBookId(), "Orders are successfully completed.");
            }
            catch (OptimisticLockingFailureException ofe) {
                responseMessages.put(bookOrder.getBookId(), "Orders could not successfully completed " +
                        "due to stock quantity is changed.");
            }
            catch (Exception e) {
                responseMessages.put(bookOrder.getBookId(), "Orders could not successfully completed:"
                        + e.getMessage());
            }
        });

        response.setData(responseMessages);
        return response;
    }

    @Override
    public BaseApiResponse getOrder(String orderId)
    {
        BaseApiResponse response = new BaseApiResponse.Builder().build();

        try
        {
            OrderDTO order = getOrderIfExist(orderId);
            response.setData(order);
            response.setResponseMessage("Order is retrieved successfully.");
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            response.setResponseMessage("Order could not retrieved successfully.");
        }

        return response;
    }

    @SneakyThrows
    @Override
    public OrderDTO getOrderIfExist(String orderId)
    {
        if(orderId.isEmpty() || Objects.isNull(orderId)) {
            throw new Exception("Order identity code not found.");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order could not found"));

        return order.toDTO();
    }

    @Override
    public BaseApiResponse getOrderInInterval(GetOrderInIntervalRequest request)
    {
        BaseApiResponse response = new BaseApiResponse.Builder().build();

        try
        {
            String minusStartDate = request.getStartDate().minusDays(1).toString();
            String plusEndDate = request.getEndDate().plusDays(1).toString();

            Optional<List<Order>> orders = orderRepository.findByCreatedTimeBetween(minusStartDate, plusEndDate);

            boolean isOrderExist = orders.isPresent() && orders.get().size() > 0;
            if(isOrderExist)
            {
                List<OrderDTO> orderDTOS = orders.get().parallelStream().map(Order::toDTO)
                        .collect(Collectors.toList());
                response.setData(orderDTOS);
                response.setResponseMessage("Orders in date interval successfully retrieved.");
            }
            else
                response.setResponseMessage("There is no any order in that date interval.");
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            response.setResponseMessage("Order could not retrieved successfully.");
        }

        return response;
    }

    private void createOrderDocument(String bookId, String customerId) {
        Order newOrder = Order.builder()
                .bookId(bookId)
                .customerId(customerId)
                .status(OrderStatus.OPENED)
                .createdTime(LocalDate.now().toString()).build();

        orderRepository.insert(newOrder);
    }

    @SneakyThrows
    private void reduceBookStockIfExist(String bookId, int numberOfBookOrdered)
    {
        BookStock bookStock = bookStockService.getBookStockIfExist(bookId);

        boolean isBookHasEnoughStock = bookStock.getQuantity() - numberOfBookOrdered >= 0;

        if(!isBookHasEnoughStock)
            throw new Exception("Stock is not enough for book:" + bookId);

        int newStockQuantity = bookStock.getQuantity() - numberOfBookOrdered;
        bookStockService.setBookStock(bookStock, newStockQuantity);
    }
}
