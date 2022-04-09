package app.service.impl;

import app.api.request.CreateBookRequest;
import app.api.response.BaseApiResponse;
import app.domain.Book;
import app.dto.BookDTO;
import app.exception.ApiError;
import app.repository.BookRepository;
import app.service.BookService;
import app.util.ApiResponseUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService
{
    private BookRepository bookRepository;

    @Override
    public BaseApiResponse createBook(CreateBookRequest request)
    {
        try
        {
            Book newBook = Book.builder()
                               .name(request.getBookName())
                               .author(request.getBookAuthor())
                               .price(request.getBookPrice()).build();

            newBook = bookRepository.insert(newBook);
            BookDTO newBookDTO = newBook.toDTO();

            return ApiResponseUtil.sendSuccessfulServiceResponse(newBookDTO,
                    "Book is registered successfully.");
        }
        catch (Exception e) {
            return ApiResponseUtil.sendUnsuccessfulServiceResponse(e, ApiError.BOOK_NOT_REGISTERED);
        }
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

    @SneakyThrows
    @Override
    public List<BookDTO> getBooksIfExist(List<String> bookIds)
    {
        if(bookIds.isEmpty() || Objects.isNull(bookIds))
            throw new Exception("Book identity list cannot be empty.");

        List<Book> books = bookRepository.findByIdIn(bookIds)
                .orElseThrow(() -> new Exception("Books could not found."));

        if(books.size() != bookIds.size())
            throw new Exception("There are book identities that could not found.");

        return books.parallelStream().map(Book::toDTO).collect(Collectors.toList());
    }
}
