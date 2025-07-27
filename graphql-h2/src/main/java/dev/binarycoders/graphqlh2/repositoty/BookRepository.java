package dev.binarycoders.graphqlh2.repositoty;

import dev.binarycoders.graphqlh2.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
