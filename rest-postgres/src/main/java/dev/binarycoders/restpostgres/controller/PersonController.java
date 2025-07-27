package dev.binarycoders.restpostgres.controller;

import dev.binarycoders.restpostgres.controller.mapper.PersonMapper;
import dev.binarycoders.restpostgres.exception.PersonNotFoundException;
import dev.binarycoders.restpostgres.exception.UpdateIdMatchException;
import dev.binarycoders.restpostgres.model.Person;
import dev.binarycoders.restpostgres.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static dev.binarycoders.restpostgres.controller.mapper.PersonMapper.toApi;
import static dev.binarycoders.restpostgres.controller.mapper.PersonMapper.toEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/people")
public class PersonController {

    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody final Person person) {
        final var created = personService.create(toEntity(person));

        return toApi(created);
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable final Long id) {
        final var found = personService.findById(id)
                .orElseThrow(PersonNotFoundException::new);

        return toApi(found);
    }

    @GetMapping
    public List<Person> findAllActive() {
        final var found = personService.findAllActive();

        return found.stream()
                .map(PersonMapper::toApi)
                .toList();
    }

    @PutMapping("/{id}")
    public Person update(@PathVariable final Long id, @RequestBody final Person person) {
        if (person.getId() != null && !person.getId().equals(id)) {
            throw new UpdateIdMatchException();
        }

        final var updated = personService.update(id, person);

        return toApi(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final Long id) {
        personService.delete(id);
    }
}

