package app.service.impl;

import app.api.request.CreateCustomerRequest;
import app.api.response.BaseApiResponse;
import app.domain.Customer;
import app.dto.CustomerDTO;
import app.repository.CustomerRepository;
import app.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceImpl implements CustomerService
{
    private CustomerRepository customerRepository;

    @Override
    public BaseApiResponse getCustomers()
    {
        BaseApiResponse response = new BaseApiResponse.Builder().build();

        try
        {
            List<Customer> customers = customerRepository.findAll();

            if (!customers.isEmpty()) {
                List<CustomerDTO> customerDTOS = customers.
                        parallelStream().map(Customer::toDTO).collect(Collectors.toList());

                response.setData(customerDTOS);
                response.setResponseMessage("Customers are found successfully.");
            }
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            response.setResponseMessage("While fetching customers, an error occured.");
        }

        return response;
    }

    @Override
    public BaseApiResponse createCustomer(CreateCustomerRequest request)
    {
        BaseApiResponse response = new BaseApiResponse.Builder().build();

        try
        {
            Customer newCustomer = Customer.builder()
                                           .name(request.getCustomerName())
                                           .surname(request.getCustomerSurname())
                                           .createdTime(LocalDate.now())
                                           .build();

            newCustomer = customerRepository.insert(newCustomer);
            CustomerDTO newCustomerDTO = newCustomer.toDTO();

            response.setData(newCustomerDTO);
            response.setResponseMessage("Customer is registered successfully.");
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            response.setResponseMessage("Customer could not registered successfully.");
        }

        return response;
    }
}
