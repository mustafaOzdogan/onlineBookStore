package app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDTO
{
    private String bookId;
    private String bookName;
    private String bookAuthor;
    private String bookCategory;
}
