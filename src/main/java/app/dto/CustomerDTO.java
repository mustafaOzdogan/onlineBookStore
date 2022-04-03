package app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {
    private String customerName;
    private String customerSurname;
    private String customerNumber;
}
