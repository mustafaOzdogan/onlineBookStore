package app.api.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class UpdateBookStockRequest extends BaseApiRequest
{
    @NotNull(message = "Book stock quantity is mandatory")
    @Min(value = 0)
    private Integer bookStockQuantity;
}
