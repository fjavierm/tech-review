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
public class PersonInput {
    private String name;
    private String email;
    private int age;
    private String bio;

    public Person toPerson() {
        return Person.builder()
                .name(this.name)
                .email(this.email)
                .age(this.age)
                .bio(this.bio)
                .build();
    }
}
