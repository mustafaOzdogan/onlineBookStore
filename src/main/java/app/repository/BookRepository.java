package app.repository;

import app.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    boolean existsById(String bookIds);
    Optional<List<Book>> findByIdIn(List<String> bookIds);
}
