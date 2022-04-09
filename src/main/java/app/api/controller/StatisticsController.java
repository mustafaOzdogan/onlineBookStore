package app.api.controller;

import app.api.constans.ApiEndpoints;
import app.api.response.BaseApiResponse;
import app.service.StatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ApiEndpoints.STATISTICS_API,
        produces = {ApiEndpoints.RESPONSE_CONTENT_TYPE})
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StatisticsController
{
    private StatisticsService statisticsService;

    @GetMapping(value = "/customers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseApiResponse getStatisticsOfCustomer(@PathVariable String customerId) {
        return statisticsService.getStatisticsOfCustomer(customerId);

    }
}
