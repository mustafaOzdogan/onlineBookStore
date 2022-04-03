package app.api.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseApiRequest
{
    private static final Logger LOG = LoggerFactory.getLogger(BaseApiRequest.class);

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = "";
        try {
            jsonObject = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            String message = "Json parsing exception for " + e.getMessage();
            LOG.info(message);
        }
        return jsonObject;
    }
}
