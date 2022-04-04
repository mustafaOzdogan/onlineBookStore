package app.api.request;

import app.dto.BookOrderDTO;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class CreateOrderRequest extends BaseApiRequest
{
    @NotNull
    private List<BookOrderDTO> books;

    @NotNull
    private String customerId;
}
