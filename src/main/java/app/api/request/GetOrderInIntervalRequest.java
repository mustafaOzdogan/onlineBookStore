package app.api.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class GetOrderInIntervalRequest extends BaseApiRequest
{
    @NotBlank(message = "Start date is mandatory")
    private LocalDate startDate;

    @NotBlank(message = "End date is mandatory")
    private LocalDate endDate;
}
