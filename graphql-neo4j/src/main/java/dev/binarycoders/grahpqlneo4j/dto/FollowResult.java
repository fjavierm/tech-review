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
public class FollowResult {
    private boolean success;
    private String message;
    private Person follower;
    private Person followee;

    public static FollowResult success(Person follower, Person followee) {
        return FollowResult.builder()
                .success(true)
                .message("Follow relationship created successfully")
                .follower(follower)
                .followee(followee)
                .build();
    }

    public static FollowResult failure(String message) {
        return FollowResult.builder()
                .success(false)
                .message(message)
                .build();
    }
}
