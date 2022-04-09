package app.domain;

import app.dto.BookOrderDTO;
import app.dto.OrderDTO;
import app.dto.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@Document
@Builder
public class Order
{
    @Id
    private String id;
    private String customerId;
    private List<BookOrderDTO> books;
    private String createdTime;
    private OrderStatus status;
    private BigDecimal totalPrice;

    public OrderDTO toDTO() {
        return OrderDTO.builder()
                .orderId(this.id)
                .orderedByCustomerId(this.customerId)
                .orderedBooks(this.books)
                .orderCreateTime(this.createdTime)
                .orderStatus(this.status).build();
    }
}
