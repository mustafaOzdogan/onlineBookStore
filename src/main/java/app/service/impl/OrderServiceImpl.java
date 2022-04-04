package app.service.impl;

import app.api.request.CreateOrderRequest;
import app.api.request.GetOrderInIntervalRequest;
import app.api.response.BaseApiResponse;
import app.domain.Book;
import app.domain.Order;
import app.dto.BookDTO;
import app.dto.OrderDTO;
import app.dto.enums.OrderStatus;
import app.repository.BookStockRepository;
import app.repository.OrderRepository;
import app.service.BookService;
import app.service.BookStockService;
import app.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.stereotype.Service;

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

        request.getBooks().parallelStream().forEach(bookOrder ->
        {
            try
            {
                BookDTO book = bookService.getBookIfExist(bookOrder.getBookId());

                boolean isBookHasEnoughStock = bookStockService.hasAvailableStock(bookOrder.getBookId(),
                        bookOrder.getNumberOfBookOrdered());

                if(!isBookHasEnoughStock)
                    throw new Exception("Stock is not enough for book:" + bookOrder.getBookId());

                Order newOrder = Order.builder()
                                      .bookId(book.getBookId())
                                      .customerId(request.getCustomerId())
                                      .status(OrderStatus.OPENED)
                                      .createdTime(LocalDate.now()).build();

                orderRepository.insert(newOrder);
                responseMessages.put(bookOrder.getBookId(), "Orders are successfully completed.");
            }
            catch (Exception e) {
                responseMessages.put(bookOrder.getBookId(), "Orders could not successfully completed:"
                        + e.getMessage());
            }

        });

        response.setSuccess(true);
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

        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            response.setResponseMessage("Order could not retrieved successfully.");
        }

        return response;
    }
}
