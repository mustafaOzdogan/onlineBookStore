package app.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BookDTO
{
    private String bookId;
    private String bookName;
    private String bookAuthor;
    private BigDecimal bookPrice;
}
