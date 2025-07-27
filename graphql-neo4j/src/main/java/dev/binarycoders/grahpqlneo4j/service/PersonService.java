package dev.binarycoders.grahpqlneo4j.service;

import dev.binarycoders.grahpqlneo4j.entity.Person;
import dev.binarycoders.grahpqlneo4j.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        log.debug("Finding all people");
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Person> findById(Long id) {
        log.debug("Finding person by id: {}", id);
        return personRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Person> findByName(String name) {
        log.debug("Finding person by name: {}", name);
        return personRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Person> searchByName(String name) {
        log.debug("Searching people by name containing: {}", name);
        return personRepository.findByNameContainingIgnoreCase(name);
    }

    public Person save(Person person) {
        log.debug("Saving person: {}", person.getName());
        return personRepository.save(person);
    }

    public boolean deleteById(Long id) {
        log.debug("Attempting to delete person with id: {}", id);
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            log.info("Successfully deleted person with id: {}", id);
            return true;
        }
        log.warn("Person with id {} not found for deletion", id);
        return false;
    }

    @Transactional(readOnly = true)
    public List<Person> findByAgeGreaterThan(int age) {
        log.debug("Finding people older than: {}", age);
        return personRepository.findByAgeGreaterThan(age);
    }

    @Transactional(readOnly = true)
    public List<Person> findByAgeLessThan(int age) {
        log.debug("Finding people younger than: {}", age);
        return personRepository.findByAgeLessThan(age);
    }

    @Transactional(readOnly = true)
    public List<Person> findFriendsByPersonId(Long personId) {
        log.debug("Finding friends for person id: {}", personId);
        return personRepository.findFriendsByPersonId(personId);
    }

    @Transactional(readOnly = true)
    public List<Person> findFollowingByPersonId(Long personId) {
        log.debug("Finding following for person id: {}", personId);
        return personRepository.findFollowingByPersonId(personId);
    }

    @Transactional(readOnly = true)
    public List<Person> findFollowersByPersonId(Long personId) {
        log.debug("Finding followers for person id: {}", personId);
        return personRepository.findFollowersByPersonId(personId);
    }

    public boolean createFriendship(Long person1Id, Long person2Id) {
        log.debug("Creating friendship between {} and {}", person1Id, person2Id);
        try {
            personRepository.createFriendship(person1Id, person2Id);
            log.info("Successfully created friendship between {} and {}", person1Id, person2Id);
            return true;
        } catch (Exception e) {
            log.error("Failed to create friendship between {} and {}: {}", person1Id, person2Id, e.getMessage());
            return false;
        }
    }

    public boolean createFollowRelationship(Long followerId, Long followeeId) {
        log.debug("Creating follow relationship: {} follows {}", followerId, followeeId);
        try {
            personRepository.createFollowRelationship(followerId, followeeId);
            log.info("Successfully created follow relationship: {} follows {}", followerId, followeeId);
            return true;
        } catch (Exception e) {
            log.error("Failed to create follow relationship: {} follows {}: {}", followerId, followeeId, e.getMessage());
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<Person> findFriendsOfFriends(Long personId) {
        log.debug("Finding friends of friends for person id: {}", personId);
        return personRepository.findFriendsOfFriends(personId);
    }

    @Transactional(readOnly = true)
    public List<Person> findConnectionsWithinNetwork(Long personId) {
        log.debug("Finding network connections for person id: {}", personId);
        return personRepository.findConnectionsWithinNetwork(personId);
    }

    @Transactional(readOnly = true)
    public List<Person> findMostPopularPeople(int limit) {
        log.debug("Finding {} most popular people", limit);
        return personRepository.findMostPopularPeople(limit);
    }

    @Transactional(readOnly = true)
    public int getFriendsCount(Long personId) {
        return findFriendsByPersonId(personId).size();
    }

    @Transactional(readOnly = true)
    public int getFollowersCount(Long personId) {
        return findFollowersByPersonId(personId).size();
    }

    @Transactional(readOnly = true)
    public int getFollowingCount(Long personId) {
        return findFollowingByPersonId(personId).size();
    }
}