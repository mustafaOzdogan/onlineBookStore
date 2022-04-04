package app.api.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class CreateCustomerRequest extends BaseApiRequest
{
    @NotBlank(message = "Customer name is mandatory")
    private String customerName;

    @NotBlank(message = "Customer surname is mandatory")
    private String customerSurname;
}
