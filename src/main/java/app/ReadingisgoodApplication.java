package app;

import app.domain.Book;
import app.domain.Customer;
import app.domain.Order;
import app.repository.BookRepository;
import app.repository.CustomerRepository;
import app.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDate;

@SpringBootApplication
@EnableMongoRepositories("app.repository")
public class ReadingisgoodApplication
{
	public static void main(String[] args) {
		SpringApplication.run(ReadingisgoodApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(BookRepository bookRepository,
							 OrderRepository orderRepository,
							 CustomerRepository customerRepository)
	{
		return args ->
		{
			// to create book collection in db
			Book testBook = Book.builder()
								.name("Test-Book-Name")
								.author("Test-Book-Author")
								.category("Test-Category")
								.build();

			bookRepository.insert(testBook);

			// to create order collection in db
			Order testOrder = new Order(LocalDate.now());
			orderRepository.insert(testOrder);

			// to create customer collection in db
			Customer testCustomer = Customer.builder()
											.name("Test-Customer-Name")
											.surname("Test-Customer-Surname")
											.createdTime(LocalDate.now())
											.build();

			customerRepository.insert(testCustomer);
		};
	}

}
