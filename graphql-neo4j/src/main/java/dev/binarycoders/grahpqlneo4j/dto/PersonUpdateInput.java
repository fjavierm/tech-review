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
public class PersonUpdateInput {
    private String name;
    private String email;
    private Integer age;
    private String bio;

    public void updatePerson(Person person) {
        if (this.name != null) person.setName(this.name);
        if (this.email != null) person.setEmail(this.email);
        if (this.age != null) person.setAge(this.age);
        if (this.bio != null) person.setBio(this.bio);
    }
}
