package app.api.controller;

import app.api.constans.ApiEndpoints;
import app.api.request.CustomerCreateRequest;
import app.api.response.BaseApiResponse;
import app.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ApiEndpoints.CUSTOMER_API,
                produces = {ApiEndpoints.RESPONSE_CONTENT_TYPE})
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController
{
    private CustomerService customerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseApiResponse getCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseApiResponse createCustomer(@RequestBody CustomerCreateRequest request) {
        return customerService.createCustomer(request);
    }
}