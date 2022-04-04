package app.api.controller;

import app.api.constans.ApiEndpoints;
import app.api.request.CreateOrderRequest;
import app.api.request.GetOrderInIntervalRequest;
import app.api.response.BaseApiResponse;
import app.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = ApiEndpoints.ORDER_API,
        produces = {ApiEndpoints.RESPONSE_CONTENT_TYPE})
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController
{
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseApiResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseApiResponse getOrder(@PathVariable String id) {
        return orderService.getOrder(id);
    }

    @PostMapping(value = "/interval")
    @ResponseStatus(HttpStatus.OK)
    public BaseApiResponse getOrderInInterval(@Valid @RequestBody GetOrderInIntervalRequest request) {
        return orderService.getOrderInInterval(request);
    }
}
