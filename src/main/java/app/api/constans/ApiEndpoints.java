package app.api.constans;

import org.springframework.http.MediaType;

public final class ApiEndpoints
{
    public static final String API_BASE_URL = "readingisgood/api";

    public static final String RESPONSE_CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8";

    public static final String CUSTOMER_API = API_BASE_URL + "/customers";

    public static final String ORDER_API = API_BASE_URL + "/orders";

    public static final String BOOK_API = API_BASE_URL + "/books";

    public static final String STATISTICS_API = API_BASE_URL + "/statistics";

}
