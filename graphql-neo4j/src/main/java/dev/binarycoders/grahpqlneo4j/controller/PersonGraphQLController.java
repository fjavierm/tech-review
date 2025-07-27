package dev.binarycoders.grahpqlneo4j.controller;

import dev.binarycoders.grahpqlneo4j.dto.FollowResult;
import dev.binarycoders.grahpqlneo4j.dto.FriendshipResult;
import dev.binarycoders.grahpqlneo4j.dto.PersonInput;
import dev.binarycoders.grahpqlneo4j.dto.PersonUpdateInput;
import dev.binarycoders.grahpqlneo4j.entity.Person;
import dev.binarycoders.grahpqlneo4j.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PersonGraphQLController {

    private final PersonService personService;

    // === QUERIES ===

    @QueryMapping
    public List<Person> allPeople() {
        log.debug("GraphQL Query: allPeople");
        return personService.findAll();
    }

    @QueryMapping
    public Person person(@Argument String id) {
        log.debug("GraphQL Query: person with id {}", id);
        return personService.findById(Long.parseLong(id)).orElse(null);
    }

    @QueryMapping
    public Person personByName(@Argument String name) {
        log.debug("GraphQL Query: personByName with name {}", name);
        return personService.findByName(name).orElse(null);
    }

    @QueryMapping
    public List<Person> searchPeople(@Argument String name) {
        log.debug("GraphQL Query: searchPeople with name {}", name);
        return personService.searchByName(name);
    }

    @QueryMapping
    public List<Person> peopleOlderThan(@Argument int age) {
        log.debug("GraphQL Query: peopleOlderThan age {}", age);
        return personService.findByAgeGreaterThan(age);
    }

    @QueryMapping
    public List<Person> peopleYoungerThan(@Argument int age) {
        log.debug("GraphQL Query: peopleYoungerThan age {}", age);
        return personService.findByAgeLessThan(age);
    }

    @QueryMapping
    public List<Person> mostPopularPeople(@Argument(value = "10") int limit) {
        log.debug("GraphQL Query: mostPopularPeople with limit {}", limit);
        return personService.findMostPopularPeople(limit);
    }

    @QueryMapping
    public List<Person> friendsOf(@Argument String personId) {
        log.debug("GraphQL Query: friendsOf person {}", personId);
        return personService.findFriendsByPersonId(Long.parseLong(personId));
    }

    @QueryMapping
    public List<Person> followersOf(@Argument String personId) {
        log.debug("GraphQL Query: followersOf person {}", personId);
        return personService.findFollowersByPersonId(Long.parseLong(personId));
    }

    @QueryMapping
    public List<Person> followingOf(@Argument String personId) {
        log.debug("GraphQL Query: followingOf person {}", personId);
        return personService.findFollowingByPersonId(Long.parseLong(personId));
    }

    @QueryMapping
    public List<Person> friendsOfFriends(@Argument String personId) {
        log.debug("GraphQL Query: friendsOfFriends for person {}", personId);
        return personService.findFriendsOfFriends(Long.parseLong(personId));
    }

    @QueryMapping
    public List<Person> networkConnections(@Argument String personId) {
        log.debug("GraphQL Query: networkConnections for person {}", personId);
        return personService.findConnectionsWithinNetwork(Long.parseLong(personId));
    }

    // === MUTATIONS ===

    @MutationMapping
    public Person createPerson(@Argument PersonInput input) {
        log.debug("GraphQL Mutation: createPerson with name {}", input.getName());
        Person person = input.toPerson();
        return personService.save(person);
    }

    @MutationMapping
    public Person updatePerson(@Argument String id, @Argument PersonUpdateInput input) {
        log.debug("GraphQL Mutation: updatePerson with id {}", id);
        return personService.findById(Long.parseLong(id))
                .map(person -> {
                    input.updatePerson(person);
                    return personService.save(person);
                })
                .orElse(null);
    }

    @MutationMapping
    public Boolean deletePerson(@Argument String id) {
        log.debug("GraphQL Mutation: deletePerson with id {}", id);
        return personService.deleteById(Long.parseLong(id));
    }

    @MutationMapping
    public FriendshipResult createFriendship(@Argument String person1Id, @Argument String person2Id) {
        log.debug("GraphQL Mutation: createFriendship between {} and {}", person1Id, person2Id);

        Long id1 = Long.parseLong(person1Id);
        Long id2 = Long.parseLong(person2Id);

        boolean success = personService.createFriendship(id1, id2);

        if (success) {
            Person person1 = personService.findById(id1).orElse(null);
            Person person2 = personService.findById(id2).orElse(null);
            return FriendshipResult.success(person1, person2);
        } else {
            return FriendshipResult.failure("Failed to create friendship");
        }
    }

    @MutationMapping
    public FollowResult followPerson(@Argument String followerId, @Argument String followeeId) {
        log.debug("GraphQL Mutation: followPerson - {} follows {}", followerId, followeeId);

        Long followerIdLong = Long.parseLong(followerId);
        Long followeeIdLong = Long.parseLong(followeeId);

        boolean success = personService.createFollowRelationship(followerIdLong, followeeIdLong);

        if (success) {
            Person follower = personService.findById(followerIdLong).orElse(null);
            Person followee = personService.findById(followeeIdLong).orElse(null);
            return FollowResult.success(follower, followee);
        } else {
            return FollowResult.failure("Failed to create follow relationship");
        }
    }

    // === SCHEMA MAPPINGS (Field Resolvers) ===

    @SchemaMapping
    public List<Person> friends(Person person) {
        return personService.findFriendsByPersonId(person.getId());
    }

    @SchemaMapping
    public List<Person> following(Person person) {
        return personService.findFollowingByPersonId(person.getId());
    }

    @SchemaMapping
    public List<Person> followers(Person person) {
        return personService.findFollowersByPersonId(person.getId());
    }

    @SchemaMapping
    public List<Person> friendsOfFriends(Person person) {
        return personService.findFriendsOfFriends(person.getId());
    }

    @SchemaMapping
    public List<Person> networkConnections(Person person) {
        return personService.findConnectionsWithinNetwork(person.getId());
    }

    @SchemaMapping
    public int friendsCount(Person person) {
        return personService.getFriendsCount(person.getId());
    }

    @SchemaMapping
    public int followersCount(Person person) {
        return personService.getFollowersCount(person.getId());
    }

    @SchemaMapping
    public int followingCount(Person person) {
        return personService.getFollowingCount(person.getId());
    }
}
