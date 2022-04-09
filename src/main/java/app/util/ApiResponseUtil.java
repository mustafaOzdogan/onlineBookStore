package app.util;

import app.api.response.BaseApiResponse;
import app.exception.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.IdGenerator;

@Slf4j
@Component
public class ApiResponseUtil
{
    private static IdGenerator idGenerator;

    public static BaseApiResponse sendUnsuccessfulServiceResponse(Exception exp, ApiError error)
    {
        BaseApiResponse response = BaseApiResponse.builder()
                                    .id(idGenerator.generateId())
                                    .success(false)
                                    .timestamp(System.currentTimeMillis())
                                    .errorMessage(exp.getMessage())
                                    .responseMessage(error.getMessage())
                                    .build();

        return response;
    }

    public static BaseApiResponse sendSuccessfulServiceResponse(Object data, String responseMessage)
    {
        return BaseApiResponse.builder()
                .data(data)
                .responseMessage(responseMessage)
                .id(idGenerator.generateId())
                .timestamp(System.currentTimeMillis())
                .success(true)
                .build();
    }
}
