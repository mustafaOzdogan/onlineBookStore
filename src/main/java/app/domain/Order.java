package app.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document
public class Order
{
    @Id
    private String id;
    private LocalDate createdTime;

    public Order(LocalDate createdTime) {
        this.createdTime = createdTime;
    }
}
