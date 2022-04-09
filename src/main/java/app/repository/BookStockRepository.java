package app.repository;

import app.domain.BookStock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookStockRepository extends MongoRepository<BookStock, String>
{
    Optional<BookStock> findBookStockByBookID(String bookId);
    boolean existsByBookIDAndAndQuantityGreaterThanEqual(String bookId, int quantity);
    Optional<List<BookStock>> findBookStockByBookIDIn(List<String> bookIds);
}
