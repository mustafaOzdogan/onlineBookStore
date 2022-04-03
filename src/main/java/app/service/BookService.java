package app.service;

import app.api.request.BookCreateRequest;
import app.api.response.BaseApiResponse;
import app.dto.BookDTO;


public interface BookService
{
    BaseApiResponse createBook(BookCreateRequest request);
    BookDTO getBookIfExist(String bookId) throws Exception;
}
