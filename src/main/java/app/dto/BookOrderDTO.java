package app.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class BookOrderDTO
{
    @NotNull
    private String bookId;

    @Positive
    private int numberOfBookOrdered;
}
