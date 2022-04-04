package app.api.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class UpdateBookStockRequest extends BaseApiRequest
{
    @NotNull
    private int bookStockQuantity;
}
