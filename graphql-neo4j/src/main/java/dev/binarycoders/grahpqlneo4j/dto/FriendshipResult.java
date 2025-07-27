package dev.binarycoders.grahpqlneo4j.dto;

import dev.binarycoders.grahpqlneo4j.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipResult {
    private boolean success;
    private String message;
    private Person person1;
    private Person person2;

    public static FriendshipResult success(Person person1, Person person2) {
        return FriendshipResult.builder()
                .success(true)
                .message("Friendship created successfully")
                .person1(person1)
                .person2(person2)
                .build();
    }

    public static FriendshipResult failure(String message) {
        return FriendshipResult.builder()
                .success(false)
                .message(message)
                .build();
    }
}
