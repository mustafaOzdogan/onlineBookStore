package app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookStockDTO
{
    private String bookStockId;
    private String bookID;
    private int bookStockQuantity;
}
