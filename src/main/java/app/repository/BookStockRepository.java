package app.repository;

import app.domain.BookStock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookStockRepository extends MongoRepository<BookStock, String>
{
    Optional<BookStock> findBookStockByBookID(String bookId);
}
