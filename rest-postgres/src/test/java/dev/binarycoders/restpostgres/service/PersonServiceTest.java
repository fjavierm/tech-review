package dev.binarycoders.restpostgres.service;

import dev.binarycoders.restpostgres.model.Person;
import dev.binarycoders.restpostgres.repository.PersonRepository;
import dev.binarycoders.restpostgres.repository.entity.PersonEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void shouldCreatePerson() {
        PersonEntity person = new PersonEntity();
        when(personRepository.save(any(PersonEntity.class))).thenReturn(person);

        PersonEntity result = personService.create(person);

        assertNotNull(result);
        verify(personRepository).save(person);
    }

    @Test
    void shouldFindPersonById() {
        Long id = 1L;
        PersonEntity person = new PersonEntity();
        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        Optional<PersonEntity> result = personService.findById(id);

        assertTrue(result.isPresent());
        verify(personRepository).findById(id);
    }

    @Test
    void shouldFindAllActive() {
        List<PersonEntity> persons = List.of(new PersonEntity());
        when(personRepository.findAllActive()).thenReturn(persons);

        List<PersonEntity> result = personService.findAllActive();

        assertFalse(result.isEmpty());
        verify(personRepository).findAllActive();
    }

    @Test
    void shouldUpdatePerson() {
        Long id = 1L;
        PersonEntity existingPerson = new PersonEntity();
        Person updateData = new Person();
        updateData.setName("John");
        when(personRepository.findById(id)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(any(PersonEntity.class))).thenReturn(existingPerson);

        PersonEntity result = personService.update(id, updateData);

        assertNotNull(result);
        verify(personRepository).save(any(PersonEntity.class));
    }

    @Test
    void shouldDeletePerson() {
        Long id = 1L;
        PersonEntity person = new PersonEntity();
        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        personService.delete(id);

        verify(personRepository).findById(id);
        verify(personRepository).save(any(PersonEntity.class));
    }
}