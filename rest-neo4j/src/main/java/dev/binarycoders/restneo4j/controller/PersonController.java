package dev.binarycoders.restneo4j.controller;

import dev.binarycoders.restneo4j.model.Person;
import dev.binarycoders.restneo4j.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> person = personService.findById(id);
        return person.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Person> getPersonByName(@PathVariable String name) {
        Optional<Person> person = personService.findByName(name);
        return person.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.save(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        Optional<Person> person = personService.findById(id);
        if (person.isPresent()) {
            Person existingPerson = person.get();
            existingPerson.setName(personDetails.getName());
            existingPerson.setEmail(personDetails.getEmail());
            existingPerson.setAge(personDetails.getAge());
            return ResponseEntity.ok(personService.save(existingPerson));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (personService.findById(id).isPresent()) {
            personService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{name}/friends")
    public List<Person> getFriends(@PathVariable String name) {
        return personService.findFriendsByPersonName(name);
    }

    @PostMapping("/{person1}/befriend/{person2}")
    public ResponseEntity<String> createFriendship(@PathVariable String person1, @PathVariable String person2) {
        personService.createFriendship(person1, person2);
        return ResponseEntity.ok("Friendship created between " + person1 + " and " + person2);
    }

    @GetMapping("/{name}/friends-of-friends")
    public List<Person> getFriendsOfFriends(@PathVariable String name) {
        return personService.findFriendsOfFriends(name);
    }

    @GetMapping("/older-than/{age}")
    public List<Person> getPersonsOlderThan(@PathVariable int age) {
        return personService.findByAgeGreaterThan(age);
    }
}
