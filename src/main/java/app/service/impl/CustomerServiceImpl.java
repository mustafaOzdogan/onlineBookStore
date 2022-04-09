package app.service.impl;

import app.api.request.CreateCustomerRequest;
import app.api.response.BaseApiResponse;
import app.domain.Customer;
import app.dto.CustomerDTO;
import app.exception.ApiError;
import app.repository.CustomerRepository;
import app.service.CustomerService;
import app.util.ApiResponseUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService
{
    private CustomerRepository customerRepository;

    @Override
    public BaseApiResponse getCustomers()
    {
        try
        {
            List<Customer> customers = customerRepository.findAll();

            if (!customers.isEmpty()) {
                List<CustomerDTO> customerDTOS = customers.
                        parallelStream().map(Customer::toDTO).collect(Collectors.toList());

                return ApiResponseUtil.sendSuccessfulServiceResponse(customerDTOS,
                        "Customers are found successfully.");
            }
            else {
                return ApiResponseUtil.sendSuccessfulServiceResponse(null,
                        "There is no any customers.");
            }
        }
        catch (Exception e) {
            return ApiResponseUtil.sendUnsuccessfulServiceResponse(e, ApiError.CUSTOMER_NOT_FETCHED);
        }
    }

    @Override
    public BaseApiResponse createCustomer(CreateCustomerRequest request)
    {
        try
        {
            Customer newCustomer = Customer.builder()
                                           .name(request.getCustomerName())
                                           .surname(request.getCustomerSurname())
                                           .createdTime(LocalDate.now().toString())
                                           .build();

            newCustomer = customerRepository.insert(newCustomer);
            CustomerDTO newCustomerDTO = newCustomer.toDTO();

            return ApiResponseUtil.sendSuccessfulServiceResponse(newCustomerDTO,
                    "Customer is registered successfully.");
        }
        catch (Exception e) {
            return ApiResponseUtil.sendUnsuccessfulServiceResponse(e, ApiError.CUSTOMER_NOT_REGISTERED);
        }
    }

    @SneakyThrows
    @Override
    public CustomerDTO getCustomerIfExist(String customerId)
    {
        if(customerId.isEmpty() || Objects.isNull(customerId)) {
            throw new Exception("Customer identity code not found.");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new Exception("Customer could not found"));

        return customer.toDTO();
    }
}
