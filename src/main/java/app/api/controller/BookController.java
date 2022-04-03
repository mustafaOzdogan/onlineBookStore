package app.api.controller;

import app.api.constans.ApiEndpoints;
import app.api.request.BaseApiRequest;
import app.api.request.BookCreateRequest;
import app.api.request.BookStockUpdateRequest;
import app.api.request.CustomerCreateRequest;
import app.api.response.BaseApiResponse;
import app.domain.BookStock;
import app.service.BookService;
import app.service.BookStockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ApiEndpoints.BOOK_API,
                produces = {ApiEndpoints.RESPONSE_CONTENT_TYPE})
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookController
{
    private BookService bookService;
    private BookStockService bookStockService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseApiResponse createBook(@RequestBody BookCreateRequest request) {
        return bookService.createBook(request);
    }

    @PatchMapping(value = "/{id}/stocks")
    @ResponseStatus(HttpStatus.OK)
    public BaseApiResponse updateBookStockQuantity(@PathVariable String id,
                                                   @RequestBody BookStockUpdateRequest request) {
        return bookStockService.updateStockQuantity(id, request);
    }
}
