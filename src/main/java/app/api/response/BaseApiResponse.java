package app.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
public class BaseApiResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private UUID id;
    private Object data;

    @NotNull
    private Long timestamp;
    private boolean success = true;
    private String errorMessage;
    private String responseMessage;
    private String correlationId = MDC.get("CID");
}
