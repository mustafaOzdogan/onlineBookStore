package app.api.controller;

import app.api.constans.ApiEndpoints;
import app.api.request.CreateBookRequest;
import app.api.request.UpdateBookStockRequest;
import app.api.response.BaseApiResponse;
import app.service.BookService;
import app.service.BookStockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public BaseApiResponse createBook(@Valid @RequestBody CreateBookRequest request) {
        return bookService.createBook(request);
    }

    @PatchMapping(value = "/{id}/stocks")
    @ResponseStatus(HttpStatus.OK)
    public BaseApiResponse updateBookStockQuantity(@PathVariable String id,
                                                   @Valid @RequestBody UpdateBookStockRequest request) {
        return bookStockService.updateStockQuantity(id, request);
    }
}
