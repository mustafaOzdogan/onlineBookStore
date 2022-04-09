package app.api.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class CreateBookRequest extends BaseApiRequest
{
    @NotBlank(message = "Book name is mandatory")
    private String bookName;

    @NotBlank(message = "Book name is mandatory")
    private String bookAuthor;

    @Positive
    @NotNull(message = "Book price is mandatory")
    private BigDecimal bookPrice;
}
