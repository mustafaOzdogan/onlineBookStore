package app.service.impl;

import app.api.response.BaseApiResponse;
import app.dto.CustomerDTO;
import app.service.CustomerService;
import app.service.StatisticsService;
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
        BaseApiResponse response = new BaseApiResponse.Builder().build();

        try
        {
            CustomerDTO customerDTO = customerService.getCustomerIfExist(customerId);


        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            response.setResponseMessage("Order could not retrieved successfully.");
        }

        return response;
    }
}
