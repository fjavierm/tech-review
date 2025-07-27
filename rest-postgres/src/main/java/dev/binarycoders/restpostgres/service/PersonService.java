package dev.binarycoders.restpostgres.service;

import dev.binarycoders.restpostgres.model.Person;
import dev.binarycoders.restpostgres.exception.PersonNotFoundException;
import dev.binarycoders.restpostgres.repository.PersonRepository;
import dev.binarycoders.restpostgres.repository.entity.PersonEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public PersonEntity create(final PersonEntity entity) {
        return personRepository.save(entity);
    }

    public Optional<PersonEntity> findById(final Long id) {
        return personRepository.findById(id);
    }

    public List<PersonEntity> findAllActive() {
        return personRepository.findAllActive();
    }

    public PersonEntity update(final Long id, final Person person) {
        final var found = findById(id);

        if (found.isEmpty()) {
            throw new PersonNotFoundException();
        }

        return update(found.get(), person);
    }

    public PersonEntity update(final PersonEntity entity, final Person person) {
        entity.setName(person.getName());
        entity.setSurname(person.getSurname());
        entity.setGender(person.getGender());
        entity.setBirthDate(person.getBirthDate());
        entity.setContactType(person.getContactType());
        entity.setContactValue(person.getContactValue());

        return personRepository.save(entity);
    }

    public void delete(Long id) {
        personRepository.findById(id).ifPresent(person -> {
            person.setDeleted(true);
            personRepository.save(person);
        });
    }
}
