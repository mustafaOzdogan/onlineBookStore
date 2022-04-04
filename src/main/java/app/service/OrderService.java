package app.service;

import app.api.request.CreateOrderRequest;
import app.api.request.GetOrderInIntervalRequest;
import app.api.response.BaseApiResponse;
import app.dto.OrderDTO;

public interface OrderService
{
    BaseApiResponse createOrder(CreateOrderRequest request);
    BaseApiResponse getOrder(String orderId);
    OrderDTO getOrderIfExist(String orderId);
    BaseApiResponse getOrderInInterval(GetOrderInIntervalRequest request);
}
