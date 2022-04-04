package app.api.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class GetOrderInIntervalRequest extends BaseApiRequest
{
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
