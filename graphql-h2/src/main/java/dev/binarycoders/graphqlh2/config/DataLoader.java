package dev.binarycoders.graphqlh2.config;

import dev.binarycoders.graphqlh2.entity.Book;
import dev.binarycoders.graphqlh2.entity.Reservation;
import dev.binarycoders.graphqlh2.entity.User;
import dev.binarycoders.graphqlh2.repositoty.BookRepository;
import dev.binarycoders.graphqlh2.repositoty.ReservationRepository;
import dev.binarycoders.graphqlh2.repositoty.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create sample users
        User user1 = userRepository.save(new User("John Doe", "john@example.com"));
        User user2 = userRepository.save(new User("Jane Smith", "jane@example.com"));

        // Create sample books
        Book book1 = bookRepository.save(new Book("The Great Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5"));
        Book book2 = bookRepository.save(new Book("To Kill a Mockingbird", "Harper Lee", "978-0-06-112008-4"));
        Book book3 = bookRepository.save(new Book("1984", "George Orwell", "978-0-452-28423-4"));

        // Create sample reservations
        reservationRepository.save(new Reservation(user1, book1, LocalDate.now(), LocalDate.now().plusDays(14)));
        reservationRepository.save(new Reservation(user2, book2, LocalDate.now().minusDays(7), LocalDate.now().plusDays(7)));
        reservationRepository.save(new Reservation(user1, book3, LocalDate.now().minusDays(3), null));

        System.out.println("Sample data loaded successfully!");
    }
}
