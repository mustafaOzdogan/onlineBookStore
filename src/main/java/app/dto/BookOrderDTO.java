package app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class BookOrderDTO
{
    @NotBlank(message = "Book id is mandatory")
    private String bookId;

    @NotNull(message = "Number of ordered book id is mandatory")
    @Positive
    private int numberOfBookOrdered;
}
