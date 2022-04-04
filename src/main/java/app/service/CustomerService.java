package app.service;

import app.api.request.CreateCustomerRequest;
import app.api.response.BaseApiResponse;

public interface CustomerService {
    BaseApiResponse getCustomers();
    BaseApiResponse createCustomer(CreateCustomerRequest request);
}
