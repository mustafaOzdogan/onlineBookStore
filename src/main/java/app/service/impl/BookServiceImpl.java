package app.service.impl;

import app.api.request.CreateBookRequest;
import app.api.response.BaseApiResponse;
import app.domain.Book;
import app.dto.BookDTO;
import app.repository.BookRepository;
import app.service.BookService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService
{
    private BookRepository bookRepository;

    @Override
    public BaseApiResponse createBook(CreateBookRequest request)
    {
        BaseApiResponse response = new BaseApiResponse.Builder().build();

        try
        {
            Book newBook = Book.builder()
                               .name(request.getBookName())
                               .author(request.getBookAuthor())
                               .category(request.getBookCategory()).build();

            newBook = bookRepository.insert(newBook);
            BookDTO newBookDTO = newBook.toDTO();

            response.setData(newBookDTO);
            response.setResponseMessage("Book is registered successfully.");
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
            response.setResponseMessage("Book could not registered successfully.");
        }

        return response;
    }

    @SneakyThrows
    @Override
    public BookDTO getBookIfExist(String bookId)
    {
        if(bookId.isEmpty() || Objects.isNull(bookId)) {
            throw new Exception("Book identity code not found.");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new Exception("Book could not found"));

        return book.toDTO();
    }
}
