package app.service.impl;

import app.api.request.CreateOrderRequest;
import app.api.request.GetOrderInIntervalRequest;
import app.api.response.BaseApiResponse;
import app.domain.BookStock;
import app.domain.Order;
import app.dto.BookDTO;
import app.dto.BookOrderDTO;
import app.dto.CustomerDTO;
import app.dto.OrderDTO;
import app.dto.enums.OrderStatus;
import app.exception.ApiError;
import app.repository.OrderRepository;
import app.service.BookService;
import app.service.BookStockService;
import app.service.CustomerService;
import app.service.OrderService;
import app.util.ApiResponseUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService
{
    private BookService bookService;
    private OrderRepository orderRepository;
    private CustomerService customerService;
    private BookStockService bookStockService;

    @Override
    @Transactional
    public BaseApiResponse createOrder(CreateOrderRequest request)
    {
        try
        {
            CustomerDTO customerDTO = customerService.getCustomerIfExist(request.getCustomerId());

            List<String> bookIds = request.getBookOrders().parallelStream()
                    .map(bookOrder -> bookOrder.getBookId()).collect(Collectors.toList());

            // check ordered books are exist
            List<BookDTO> books = bookService.getBooksIfExist(bookIds);

            List<BookStock> bookStocks = bookStockService.getBookStocks(bookIds);

            // check all of the books in order whether they have enough stock
            checkBookStocksIsEnough(bookStocks, request.getBookOrders());

            Map<String, Integer> bookIdOrderQuantityMapping = request.getBookOrders().parallelStream()
                    .collect(Collectors.toMap(BookOrderDTO::getBookId, BookOrderDTO::getNumberOfBookOrdered));

            bookStocks.parallelStream().forEach(bookStock -> {
                int reducedBookStock = bookIdOrderQuantityMapping.get(bookStock.getBookID());
                reduceBookStock(bookStock, reducedBookStock);
            });

            createOrderDocument(books, request.getBookOrders(), customerDTO.getCustomerId());

            return ApiResponseUtil.sendSuccessfulServiceResponse(null,
                    "Order step is successfully completed.");
        }
        catch (Exception e) {
            return ApiResponseUtil.sendUnsuccessfulServiceResponse(e, ApiError.ORDER_NOT_COMPLETED);
        }
    }

    @SneakyThrows
    private void checkBookStocksIsEnough(List<BookStock> bookStocks, List<BookOrderDTO> bookOrders)
    {
        Map<String, Integer> bookIdStockQuantityMapping = bookStocks.parallelStream()
                .collect(Collectors.toMap(BookStock::getBookID, BookStock::getQuantity));

        Predicate<BookOrderDTO> stockNotEnoughCondition = order ->
                bookIdStockQuantityMapping.get(order.getBookId()) < order.getNumberOfBookOrdered();

        Optional<BookOrderDTO> insufficientStock = bookOrders.parallelStream()
                .filter(stockNotEnoughCondition).findFirst();

        if(insufficientStock.isPresent())
            throw new Exception("Stock not enough for book with id :" + insufficientStock.get().getBookId());
    }

    private void createOrderDocument(List<BookDTO> books, List<BookOrderDTO> bookOrders, String customerId)
    {
        Map<String, BigDecimal> bookIdPriceMapping = books.parallelStream()
                .collect(Collectors.toMap(BookDTO::getBookId, BookDTO::getBookPrice));

        // sum of all book prices in the order
        BigDecimal totalPrice = bookOrders.parallelStream()
                .map(bookOrder -> bookIdPriceMapping.get(bookOrder.getBookId())
                        .multiply(BigDecimal.valueOf(bookOrder.getNumberOfBookOrdered())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order newOrder = Order.builder()
                .books(bookOrders)
                .customerId(customerId)
                .status(OrderStatus.OPENED)
                .totalPrice(totalPrice)
                .createdTime(LocalDate.now().toString()).build();

        orderRepository.insert(newOrder);
    }

    @SneakyThrows
    private void reduceBookStock(BookStock bookStock, int numberOfBookOrdered)
    {
        int newStockQuantity = bookStock.getQuantity() - numberOfBookOrdered;
        bookStockService.setBookStock(bookStock, newStockQuantity);
    }

    @Override
    public BaseApiResponse getOrder(String orderId)
    {
        try
        {
            OrderDTO order = getOrderIfExist(orderId);

            return ApiResponseUtil.sendSuccessfulServiceResponse(order,
                    "Order is retrieved successfully.");
        }
        catch (Exception e) {
            return ApiResponseUtil.sendUnsuccessfulServiceResponse(e, ApiError.ORDER_NOT_RETRIEVED);
        }
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

                return ApiResponseUtil.sendSuccessfulServiceResponse(orderDTOS,
                        "Orders in date interval successfully retrieved.");
            }
            else {
                return ApiResponseUtil.sendSuccessfulServiceResponse(null,
                        "There is no any order in that date interval.");
            }
        }
        catch (Exception e) {
            return ApiResponseUtil.sendUnsuccessfulServiceResponse(e,
                    ApiError.ORDER_NOT_RETRIEVED);
        }
    }
}
