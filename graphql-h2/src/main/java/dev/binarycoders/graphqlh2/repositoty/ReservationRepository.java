package dev.binarycoders.graphqlh2.repositoty;

import dev.binarycoders.graphqlh2.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
