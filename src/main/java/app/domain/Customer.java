package app.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document
public class Customer
{
    @Id
    private String id;
    private String name;
    private String surname;

    @Indexed(unique = true)
    private long number;
    private LocalDate createdTime;

    public Customer(String name, String surname, long number, LocalDate createdTime) {
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.createdTime = createdTime;
    }
}
