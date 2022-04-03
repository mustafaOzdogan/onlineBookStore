package app.service;

import app.api.request.BookStockUpdateRequest;
import app.api.response.BaseApiResponse;

public interface BookStockService
{
    BaseApiResponse updateStockQuantity(String bookId, BookStockUpdateRequest request);
}
