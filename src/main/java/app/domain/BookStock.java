package app.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Builder
@Data
@Document
public class BookStock
{
    @Id
    private String id;

    private String bookID;
    private int quantity;
    private LocalDate lastUpdatedTime;

    @Version
    private long version;
}
