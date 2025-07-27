package dev.binarycoders.restneo4j.service;

import dev.binarycoders.restneo4j.model.Person;
import dev.binarycoders.restneo4j.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public Optional<Person> findByName(String name) {
        return personRepository.findByName(name);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    public List<Person> findByAgeGreaterThan(int age) {
        return personRepository.findByAgeGreaterThan(age);
    }

    public List<Person> findFriendsByPersonName(String name) {
        return personRepository.findFriendsByPersonName(name);
    }

    public void createFriendship(String person1, String person2) {
        personRepository.createFriendship(person1, person2);
    }

    public List<Person> findFriendsOfFriends(String name) {
        return personRepository.findFriendsOfFriends(name);
    }
}
