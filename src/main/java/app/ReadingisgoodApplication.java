package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("app.repository")
public class ReadingisgoodApplication
{
	public static void main(String[] args) {
		SpringApplication.run(ReadingisgoodApplication.class, args);
	}
}
