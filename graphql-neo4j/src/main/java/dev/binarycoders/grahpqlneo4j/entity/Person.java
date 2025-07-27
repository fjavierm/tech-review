package dev.binarycoders.grahpqlneo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private String email;
    private int age;
    private String bio;

    @Relationship(type = "FRIENDS_WITH", direction = Relationship.Direction.OUTGOING)
    @Builder.Default
    private Set<Person> friends = new HashSet<>();

    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.OUTGOING)
    @Builder.Default
    private Set<Person> following = new HashSet<>();

    // Custom constructor for common use case
    public Person(String name, String email, int age, String bio) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.bio = bio;
        this.friends = new HashSet<>();
        this.following = new HashSet<>();
    }

    // Business logic methods
    public void addFriend(Person friend) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(friend);
    }

    public void follow(Person person) {
        if (following == null) {
            following = new HashSet<>();
        }
        following.add(person);
    }

    public int getFriendsCount() {
        return friends != null ? friends.size() : 0;
    }

    public int getFollowingCount() {
        return following != null ? following.size() : 0;
    }
}
