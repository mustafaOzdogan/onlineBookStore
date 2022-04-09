package app.service.impl;

import app.api.request.UpdateBookStockRequest;
import app.api.response.BaseApiResponse;
import app.domain.BookStock;
import app.dto.BookDTO;
import app.repository.BookStockRepository;
import app.service.BookService;
import app.service.BookStockService;
import app.util.ApiResponseUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookStockServiceImpl implements BookStockService
{
    private BookService bookService;
    private BookStockRepository bookStockRepository;

    @Override
    public BaseApiResponse updateStockQuantity(String bookId, UpdateBookStockRequest request)
    {
        try
        {
            BookDTO bookDTO = bookService.getBookIfExist(bookId);

            // get bookstock by book id if exist or create new one
            bookStockRepository.findBookStockByBookID(bookDTO.getBookId())
                    .ifPresentOrElse(bookStock -> {
                        setBookStock(bookStock, request.getBookStockQuantity()); },
                            () -> { createBookStock(bookId, request.getBookStockQuantity()); });

            return ApiResponseUtil.sendSuccessfulServiceResponse(null,
                    "Book stock is updated successfully.");
        }
        catch (Exception e) {
            return ApiResponseUtil.sendUnsuccessfulServiceResponse(e,
                    "Book stock could not updated successfully.");
        }
    }

    private void createBookStock(String bookId, int stockQuantity)
    {
        BookStock newBookStock = BookStock.builder()
                .bookID(bookId)
                .quantity(stockQuantity)
                .lastUpdatedTime(LocalDate.now().toString()).build();
        bookStockRepository.insert(newBookStock);
    }

    @Override
    public void setBookStock(BookStock bookStock, int stockQuantity)
    {
        bookStock.setQuantity(stockQuantity);
        bookStock.setLastUpdatedTime(LocalDate.now().toString());
        bookStockRepository.save(bookStock);
    }

    @SneakyThrows
    @Override
    public BookStock getBookStockIfExist(String bookId)
    {
        if(bookId.isEmpty() || Objects.isNull(bookId)) {
            throw new Exception("Book identity code not found.");
        }

        BookStock bookstock = bookStockRepository.findBookStockByBookID(bookId)
                .orElseThrow(() -> new Exception("Book stock could not found"));

        return bookstock;
    }

    @SneakyThrows
    @Override
    public List<BookStock> getBookStocks(List<String> bookIds)
    {
        if(bookIds.isEmpty() || Objects.isNull(bookIds))
            throw new Exception("Book identity list cannot be empty.");

        List<BookStock> bookStocks = bookStockRepository.findBookStockByBookIDIn(bookIds)
                .orElseThrow(() -> new Exception("Book stock could not found."));

        return bookStocks;
    }
}
