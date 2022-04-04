package app.repository;

import app.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<List<Order>> findByCreatedTimeBetween(String startDate, String endDate);
}
