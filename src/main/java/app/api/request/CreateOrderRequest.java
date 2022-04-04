package app.api.request;

import app.dto.BookOrderDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class CreateOrderRequest extends BaseApiRequest
{
    @NotNull
    private List<BookOrderDTO> books;

    @NotBlank(message = "Customer id is mandatory")
    private String customerId;
}
