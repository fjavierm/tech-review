package dev.binarycoders.graphqlh2.controller;

import dev.binarycoders.graphqlh2.entity.Book;
import dev.binarycoders.graphqlh2.entity.Reservation;
import dev.binarycoders.graphqlh2.entity.User;
import dev.binarycoders.graphqlh2.repositoty.BookRepository;
import dev.binarycoders.graphqlh2.repositoty.ReservationRepository;
import dev.binarycoders.graphqlh2.repositoty.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GraphQLController {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;

    @QueryMapping
    public List<User> users() {
        return userRepository.findAll();
    }

    @QueryMapping
    public User user(@Argument Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Book> books() {
        return bookRepository.findAll();
    }

    @QueryMapping
    public Book book(@Argument Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @QueryMapping
    public List<Reservation> reservations() {
        return reservationRepository.findAll();
    }

    @QueryMapping
    public Reservation reservation(@Argument Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @MutationMapping
    public User createUser(@Argument Map<String, Object> input) {
        User user = new User();
        user.setName((String) input.get("name"));
        user.setEmail((String) input.get("email"));
        return userRepository.save(user);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument Map<String, Object> input) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName((String) input.get("name"));
        user.setEmail((String) input.get("email"));
        return userRepository.save(user);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @MutationMapping
    public Book createBook(@Argument Map<String, Object> input) {
        Book book = new Book();
        book.setTitle((String) input.get("title"));
        book.setAuthor((String) input.get("author"));
        book.setIsbn((String) input.get("isbn"));
        return bookRepository.save(book);
    }

    @MutationMapping
    public Book updateBook(@Argument Long id, @Argument Map<String, Object> input) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle((String) input.get("title"));
        book.setAuthor((String) input.get("author"));
        book.setIsbn((String) input.get("isbn"));
        return bookRepository.save(book);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @MutationMapping
    public Reservation createReservation(@Argument Map<String, Object> input) {
        Long userId = Long.valueOf(input.get("userId").toString());
        Long bookId = Long.valueOf(input.get("bookId").toString());

        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDate.parse(input.get("reservationDate").toString()));

        if (input.get("returnDate") != null) {
            reservation.setReturnDate(LocalDate.parse(input.get("returnDate").toString()));
        }

        return reservationRepository.save(reservation);
    }

    @MutationMapping
    public Reservation updateReservation(@Argument Long id, @Argument Map<String, Object> input) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();

        Long userId = Long.valueOf(input.get("userId").toString());
        Long bookId = Long.valueOf(input.get("bookId").toString());

        User user = userRepository.findById(userId).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow();

        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDate.parse(input.get("reservationDate").toString()));

        if (input.get("returnDate") != null) {
            reservation.setReturnDate(LocalDate.parse(input.get("returnDate").toString()));
        }

        return reservationRepository.save(reservation);
    }

    @MutationMapping
    public Boolean deleteReservation(@Argument Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}