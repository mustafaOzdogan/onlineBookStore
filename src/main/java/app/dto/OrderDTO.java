package app.dto;

import app.dto.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OrderDTO
{
    private String orderId;
    private String orderedByCustomerId;
    private String orderedBookId;
    private String orderCreateTime;
    private OrderStatus orderStatus;
}
