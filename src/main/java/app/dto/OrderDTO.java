package app.dto;

import app.dto.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class OrderDTO
{
    private String orderId;
    private String orderedByCustomerId;
    private List<BookOrderDTO> orderedBooks;
    private String orderCreateTime;
    private OrderStatus orderStatus;
}
