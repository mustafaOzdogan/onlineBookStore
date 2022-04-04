package app.service.impl;

import app.api.request.UpdateBookStockRequest;
import app.api.response.BaseApiResponse;
import app.domain.BookStock;
import app.dto.BookDTO;
import app.repository.BookStockRepository;
import app.service.BookService;
import app.service.BookStockService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        BaseApiResponse response = new BaseApiResponse.Builder().build();

        try
        {
            BookDTO bookDTO = bookService.getBookIfExist(bookId);

            // get bookstock by book id if exist or create new one
            bookStockRepository.findBookStockByBookID(bookDTO.getBookId())
                    .ifPresentOrElse(bookStock -> { setBookStock(bookId, request.getBookStockQuantity()); },
                                     () -> { createBookStock(bookId, request.getBookStockQuantity()); });

            response.setResponseMessage("Book stock is updated successfully.");
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            response.setResponseMessage("Book stock could not updated successfully.");
        }

        return response;
    }

    @SneakyThrows
    @Override
    public boolean hasAvailableStock(String bookId, int stockQuantity)
    {
        if(bookId.isEmpty() || Objects.isNull(bookId)) {
            throw new Exception("Book identity code not found.");
        }

        boolean hasAvailableStock = bookStockRepository
                .existsByBookIDAndAndQuantityGreaterThanEqual(bookId, stockQuantity);

        return hasAvailableStock;
    }

    private void changeBookStock(String bookId, int stockChange)
    {
        bookStockRepository.findBookStockByBookID(bookId)
                .ifPresentOrElse(bookStock -> {
                        int newStockQuantity = bookStock.getQuantity() + stockChange;
                        bookStock.setQuantity(newStockQuantity);
                        bookStockRepository.save(bookStock);
                    },
                        () -> new Exception("Book stock could not found."));
    }

    private void createBookStock(String bookId, int stockQuantity)
    {
        BookStock newBookStock = BookStock.builder()
                .bookID(bookId)
                .quantity(stockQuantity)
                .lastUpdatedTime(LocalDate.now()).build();
        bookStockRepository.insert(newBookStock);
    }

    private void setBookStock(String bookId, int stockQuantity)
    {
        BookStock newBookStock = BookStock.builder()
                .bookID(bookId)
                .quantity(stockQuantity)
                .lastUpdatedTime(LocalDate.now()).build();
        bookStockRepository.save(newBookStock);
    }
}
