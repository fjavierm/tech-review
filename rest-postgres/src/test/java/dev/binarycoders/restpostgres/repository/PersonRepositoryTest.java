package dev.binarycoders.restpostgres.repository;

import dev.binarycoders.restpostgres.model.definition.ContactType;
import dev.binarycoders.restpostgres.model.definition.Gender;
import dev.binarycoders.restpostgres.repository.entity.PersonEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();

        PersonEntity john = new PersonEntity();
        john.setName("John");
        john.setSurname("Doe");
        john.setGender(Gender.MALE);
        john.setBirthDate(LocalDate.now().minusYears(30));
        john.setContactType(ContactType.EMAIL);
        john.setContactValue("john@mail.com");
        john.setDeleted(false);
        john.setCreatedAt(LocalDateTime.now());
        john.setUpdatedAt(LocalDateTime.now());
        personRepository.save(john);

        PersonEntity jane = new PersonEntity();
        jane.setName("Jane");
        jane.setSurname("Doe");
        jane.setGender(Gender.FEMALE);
        jane.setBirthDate(LocalDate.now().minusYears(25));
        jane.setContactType(ContactType.PHONE);
        jane.setContactValue("555-1234");
        jane.setDeleted(true);
        jane.setCreatedAt(LocalDateTime.now());
        jane.setUpdatedAt(LocalDateTime.now());
        personRepository.save(jane);
    }

    @Test
    void softDeleteById_ShouldMarkPersonAsDeleted() {
        PersonEntity person = personRepository.findAllActive().getFirst();
        personRepository.softDeleteById(person.getId());

        List<PersonEntity> activePersons = personRepository.findAllActive();
        assertTrue(activePersons.isEmpty());
    }

    @Test
    void findAllActive_ShouldReturnOnlyActivePersons() {
        List<PersonEntity> activePersons = personRepository.findAllActive();

        assertEquals(1, activePersons.size());
        assertEquals("John", activePersons.getFirst().getName());
    }
}