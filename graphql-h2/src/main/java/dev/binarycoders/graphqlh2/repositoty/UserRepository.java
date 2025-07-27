package dev.binarycoders.graphqlh2.repositoty;

import dev.binarycoders.graphqlh2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
