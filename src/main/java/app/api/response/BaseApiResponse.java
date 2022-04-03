package app.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
public class BaseApiResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String id;
    private Object data;

    @NotNull
    private Long timestamp = System.currentTimeMillis();
    private boolean success = true;
    private String errorMessage;
    private String responseMessage;
    private String correlationId = MDC.get("CID");

    public static final class Builder {
        private String id;

        private Long timestamp;

        private boolean success = true;

        private String responseCode;

        private String responseMessage;

        private String correlationId;

        public BaseApiResponse build()
        {
            BaseApiResponse response = new BaseApiResponse();

            response.id = this.id;
            response.timestamp = this.timestamp;
            response.success = this.success;
            response.responseMessage = this.responseMessage;
            response.correlationId = this.correlationId;

            return response;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withResponseCode(String responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder withResponseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
            return this;
        }

        public Builder withSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder withTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withCorrelationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }
    }
}
