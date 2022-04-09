package app.service;

import app.api.request.UpdateBookStockRequest;
import app.api.response.BaseApiResponse;
import app.domain.BookStock;

import java.util.List;

public interface BookStockService
{
    BaseApiResponse updateStockQuantity(String bookId, UpdateBookStockRequest request);
    BookStock getBookStockIfExist(String bookId);
    void setBookStock(BookStock bookStock, int stockQuantity);
    List<BookStock> getBookStocks(List<String> bookIds);
}
