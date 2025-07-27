package dev.binarycoders.restpostgres.repository;

import dev.binarycoders.restpostgres.repository.entity.PersonEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

    @Modifying
    @Query("UPDATE person SET deleted = true WHERE id = :id")
    void softDeleteById(Long id);

    @Query("SELECT * FROM person WHERE deleted = false ORDER BY id ASC")
    List<PersonEntity> findAllActive();
}
