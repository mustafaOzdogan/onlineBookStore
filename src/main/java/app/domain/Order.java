package app.domain;

import app.dto.OrderDTO;
import app.dto.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document
@Builder
public class Order
{
    @Id
    private String id;
    private String customerId;
    private String bookId;
    private LocalDate createdTime;
    private OrderStatus status;

    public OrderDTO toDTO() {
        return OrderDTO.builder()
                .orderId(this.id)
                .orderByCustomerId(this.customerId)
                .orderedBookId(this.bookId)
                .orderCreateTime(this.createdTime)
                .orderStatus(this.status).build();
    }
}
