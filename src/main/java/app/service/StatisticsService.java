package app.service;

import app.api.response.BaseApiResponse;

public interface StatisticsService {
    BaseApiResponse getStatisticsOfCustomer(String customerId);
}
