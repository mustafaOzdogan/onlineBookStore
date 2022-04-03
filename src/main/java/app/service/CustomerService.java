package app.service;

import app.api.request.CustomerCreateRequest;
import app.api.response.BaseApiResponse;

public interface CustomerService {
    BaseApiResponse getCustomers();
    BaseApiResponse createCustomer(CustomerCreateRequest request);
}
