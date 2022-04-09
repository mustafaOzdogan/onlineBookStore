package app.domain;

import app.dto.BookDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Builder
@Data
@Document
public class Book
{
    @Id
    private String id;
    private String name;
    private String author;
    private BigDecimal price;

    public BookDTO toDTO() {
        return BookDTO.builder()
                .bookId(this.id)
                .bookName(this.name)
                .bookAuthor(this.author)
                .bookPrice(this.price)
                .build();
    }
}
