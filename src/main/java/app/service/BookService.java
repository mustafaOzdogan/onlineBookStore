package app.service;

import app.api.request.CreateBookRequest;
import app.api.response.BaseApiResponse;
import app.dto.BookDTO;


public interface BookService
{
    BaseApiResponse createBook(CreateBookRequest request);
    BookDTO getBookIfExist(String bookId) throws Exception;
}
