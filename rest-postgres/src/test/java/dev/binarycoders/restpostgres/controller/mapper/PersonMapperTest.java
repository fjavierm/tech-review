package dev.binarycoders.restpostgres.controller.mapper;

import dev.binarycoders.restpostgres.model.Person;
import dev.binarycoders.restpostgres.model.definition.ContactType;
import dev.binarycoders.restpostgres.model.definition.Gender;
import dev.binarycoders.restpostgres.repository.entity.PersonEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static dev.binarycoders.restpostgres.model.definition.ContactType.EMAIL;
import static dev.binarycoders.restpostgres.model.definition.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PersonMapperTest {

    private static final Long ID = 1L;
    private static final String NAME = "John";
    private static final String SURNAME = "Doe";
    private static final Gender GENDER = MALE;
    private static final LocalDate BIRTH_DATE = LocalDate.of(1990, 1, 1);
    private static final ContactType CONTACT_TYPE = EMAIL;
    private static final String CONTACT_VALUE = "john.doe@example.com";

    @Test
    void toApi_shouldReturnNull_whenEntityIsNull() {
        assertNull(PersonMapper.toApi(null));
    }

    @Test
    void toApi_shouldMapAllFields_whenEntityIsValid() {
        final var entity = createPersonEntity();
        final var result = PersonMapper.toApi(entity);

        assertNotNull(result);
        assertEquals(ID, result.getId());
        assertEquals(NAME, result.getName());
        assertEquals(SURNAME, result.getSurname());
        assertEquals(GENDER, result.getGender());
        assertEquals(BIRTH_DATE, result.getBirthDate());
        assertEquals(CONTACT_TYPE, result.getContactType());
        assertEquals(CONTACT_VALUE, result.getContactValue());
    }

    @Test
    void toEntity_shouldReturnNull_whenPersonIsNull() {
        assertNull(PersonMapper.toEntity(null));
    }

    @Test
    void toEntity_shouldMapAllFields_whenPersonIsValid() {
        final var person = createPerson();
        final var result = PersonMapper.toEntity(person);

        assertNotNull(result);
        assertEquals(NAME, result.getName());
        assertEquals(SURNAME, result.getSurname());
        assertEquals(GENDER, result.getGender());
        assertEquals(BIRTH_DATE, result.getBirthDate());
        assertEquals(CONTACT_TYPE, result.getContactType());
        assertEquals(CONTACT_VALUE, result.getContactValue());
    }

    private PersonEntity createPersonEntity() {
        final var entity = new PersonEntity();
        entity.setId(ID);
        entity.setName(NAME);
        entity.setSurname(SURNAME);
        entity.setGender(GENDER);
        entity.setBirthDate(BIRTH_DATE);
        entity.setContactType(CONTACT_TYPE);
        entity.setContactValue(CONTACT_VALUE);
        return entity;
    }

    private Person createPerson() {
        return Person.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(BIRTH_DATE)
                .contactType(CONTACT_TYPE)
                .contactValue(CONTACT_VALUE)
                .build();
    }
}