package app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiError
{
    BOOK_NOT_REGISTERED("Book could not registered successfully."),

    CUSTOMER_NOT_FETCHED("While fetching customers, an error occured."),
    CUSTOMER_NOT_REGISTERED("Customer could not registered successfully."),
    CUSTOMER_ORDER_STATISTICS_NOT_RETRIEVED("Customer order statistics could not retrieved successfully."),

    BOOK_STOCK_NOT_UPDATED("Book stock could not updated successfully."),

    ORDER_NOT_RETRIEVED("Order could not retrieved successfully."),
    ORDER_NOT_COMPLETED("Ordering could not successfully completed.");

    private String message;
}
