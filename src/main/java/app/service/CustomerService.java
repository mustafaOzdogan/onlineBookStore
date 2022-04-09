package app.service;

import app.api.request.CreateCustomerRequest;
import app.api.response.BaseApiResponse;
import app.dto.CustomerDTO;

public interface CustomerService {
    BaseApiResponse getCustomers();
    BaseApiResponse createCustomer(CreateCustomerRequest request);
    CustomerDTO getCustomerIfExist(String customerId);
}
