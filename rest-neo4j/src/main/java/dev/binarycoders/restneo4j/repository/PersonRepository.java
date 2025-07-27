package dev.binarycoders.restneo4j.repository;

import dev.binarycoders.restneo4j.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByName(String name);

    List<Person> findByAgeGreaterThan(int age);

    @Query("MATCH (p:Person)-[:FRIENDS_WITH]->(friend:Person) WHERE p.name = $name RETURN friend")
    List<Person> findFriendsByPersonName(@Param("name") String name);

    @Query("MATCH (p1:Person {name: $person1}), (p2:Person {name: $person2}) " +
            "CREATE (p1)-[:FRIENDS_WITH]->(p2), (p2)-[:FRIENDS_WITH]->(p1)")
    void createFriendship(@Param("person1") String person1, @Param("person2") String person2);

    @Query("MATCH (p:Person)-[:FRIENDS_WITH*2]->(friend:Person) " +
            "WHERE p.name = $name AND NOT (p)-[:FRIENDS_WITH]->(friend) AND p <> friend " +
            "RETURN DISTINCT friend")
    List<Person> findFriendsOfFriends(@Param("name") String name);
}
