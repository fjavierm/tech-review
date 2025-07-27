package dev.binarycoders.grahpqlneo4j.repository;

import dev.binarycoders.grahpqlneo4j.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByName(String name);

    List<Person> findByNameContainingIgnoreCase(String name);

    List<Person> findByAgeGreaterThan(int age);

    List<Person> findByAgeLessThan(int age);

    @Query("MATCH (p:Person)-[:FRIENDS_WITH]->(friend:Person) WHERE p.id = $personId RETURN friend")
    List<Person> findFriendsByPersonId(@Param("personId") Long personId);

    @Query("MATCH (p:Person)-[:FOLLOWS]->(following:Person) WHERE p.id = $personId RETURN following")
    List<Person> findFollowingByPersonId(@Param("personId") Long personId);

    @Query("MATCH (p:Person)<-[:FOLLOWS]-(follower:Person) WHERE p.id = $personId RETURN follower")
    List<Person> findFollowersByPersonId(@Param("personId") Long personId);

    @Query("MATCH (p1:Person), (p2:Person) WHERE p1.id = $person1Id AND p2.id = $person2Id " +
            "CREATE (p1)-[:FRIENDS_WITH]->(p2), (p2)-[:FRIENDS_WITH]->(p1)")
    void createFriendship(@Param("person1Id") Long person1Id, @Param("person2Id") Long person2Id);

    @Query("MATCH (p1:Person), (p2:Person) WHERE p1.id = $followerId AND p2.id = $followeeId " +
            "CREATE (p1)-[:FOLLOWS]->(p2)")
    void createFollowRelationship(@Param("followerId") Long followerId, @Param("followeeId") Long followeeId);

    @Query("MATCH (p:Person)-[:FRIENDS_WITH*2]->(friend:Person) " +
            "WHERE p.id = $personId AND NOT (p)-[:FRIENDS_WITH]->(friend) AND p <> friend " +
            "RETURN DISTINCT friend")
    List<Person> findFriendsOfFriends(@Param("personId") Long personId);

    @Query("MATCH (p:Person)-[:FRIENDS_WITH*1..3]->(connected:Person) " +
            "WHERE p.id = $personId AND p <> connected " +
            "RETURN DISTINCT connected, length(shortestPath((p)-[:FRIENDS_WITH*]-(connected))) as distance " +
            "ORDER BY distance")
    List<Person> findConnectionsWithinNetwork(@Param("personId") Long personId);

    @Query("MATCH (p:Person) " +
            "OPTIONAL MATCH (p)-[:FRIENDS_WITH]->(friend:Person) " +
            "RETURN p, count(friend) as friendCount " +
            "ORDER BY friendCount DESC " +
            "LIMIT $limit")
    List<Person> findMostPopularPeople(@Param("limit") int limit);
}
