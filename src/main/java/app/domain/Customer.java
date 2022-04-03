package app.domain;

import app.dto.CustomerDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Builder
@Data
@Document
public class Customer
{
    @Id
    private String id;
    private String name;
    private String surname;
    private LocalDate createdTime;

    public CustomerDTO toDTO() {
        return CustomerDTO.builder()
                .customerName(this.name)
                .customerSurname(this.surname)
                .customerNumber(this.id)
                .build();
    }
}
