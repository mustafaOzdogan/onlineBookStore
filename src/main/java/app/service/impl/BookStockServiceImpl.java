package app.service.impl;

import app.api.request.BookStockUpdateRequest;
import app.api.response.BaseApiResponse;
import app.domain.BookStock;
import app.dto.BookDTO;
import app.repository.BookStockRepository;
import app.service.BookService;
import app.service.BookStockService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookStockServiceImpl implements BookStockService
{
    private BookService bookService;
    private BookStockRepository bookStockRepository;

    @Override
    public BaseApiResponse updateStockQuantity(String bookId, BookStockUpdateRequest request)
    {
        BaseApiResponse response = new BaseApiResponse.Builder().build();

        try
        {
            BookDTO bookDTO = bookService.getBookIfExist(bookId);

            // get bookstock by book id if exist or create new one
            bookStockRepository.findBookStockByBookID(bookDTO.getBookId())
                    .ifPresentOrElse(bookStock -> {
                        bookStock.setQuantity(request.getBookStockQuantity());
                        bookStock.setLastUpdatedTime(LocalDate.now());
                        bookStockRepository.save(bookStock);
                        },
                            () -> {
                        BookStock newBookStock = BookStock.builder()
                                .bookID(bookId)
                                .quantity(request.getBookStockQuantity())
                                .lastUpdatedTime(LocalDate.now()).build();
                        bookStockRepository.insert(newBookStock);
                    });

            response.setResponseMessage("Book stock is updated successfully.");
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            response.setResponseMessage("Book stock could not updated successfully.");
        }

        return response;
    }
}
