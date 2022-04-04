package app.service;

import app.api.request.UpdateBookStockRequest;
import app.api.response.BaseApiResponse;

public interface BookStockService
{
    BaseApiResponse updateStockQuantity(String bookId, UpdateBookStockRequest request);
    boolean hasAvailableStock(String bookId, int stockQuantity);
}
