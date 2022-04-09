package app.service.impl;

import app.api.response.BaseApiResponse;
import app.dto.CustomerDTO;
import app.service.CustomerService;
import app.service.StatisticsService;
import app.util.ApiResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsServiceImpl implements StatisticsService
{
    private CustomerService customerService;

    @Override
    public BaseApiResponse getStatisticsOfCustomer(String customerId)
    {
        try
        {
            CustomerDTO customerDTO = customerService.getCustomerIfExist(customerId);

            return ApiResponseUtil.sendSuccessfulServiceResponse(null, "");
        }
        catch (Exception e) {
            return ApiResponseUtil.sendUnsuccessfulServiceResponse(e,
                    "Customer order statistics could not retrieved successfully.");
        }
    }
}
