package app.domain;

import app.dto.BookStockDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document
public class BookStock
{
    @Id
    private String id;

    private String bookID;
    private int quantity;
    private String lastUpdatedTime;

    @Version
    private long version;

    public BookStockDTO toDTO() {
        return BookStockDTO.builder()
                .bookStockId(this.id)
                .bookID(this.bookID)
                .bookStockQuantity(this.quantity)
                .build();
    }
}
