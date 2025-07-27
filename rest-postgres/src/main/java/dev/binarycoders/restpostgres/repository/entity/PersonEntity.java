package dev.binarycoders.restpostgres.repository.entity;

import dev.binarycoders.restpostgres.model.definition.ContactType;
import dev.binarycoders.restpostgres.model.definition.Gender;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.Period.between;

@Data
@Table("person")
public class PersonEntity {

    @Id
    Long id;
    String name;
    String surname;
    Gender gender;
    LocalDate birthDate;
    ContactType contactType;
    String contactValue;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    boolean deleted = false;

    public int getAge() {
        return between(birthDate, LocalDate.now()).getYears();
    }
}
