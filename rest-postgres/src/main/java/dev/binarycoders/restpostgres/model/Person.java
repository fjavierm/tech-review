package dev.binarycoders.restpostgres.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.binarycoders.restpostgres.model.definition.ContactType;
import dev.binarycoders.restpostgres.model.definition.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static java.time.Period.between;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Long id;
    private String name;
    private String surname;
    private Gender gender;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;
    private ContactType contactType;
    private String contactValue;

    @JsonIgnore
    public int getAge() {
        return between(birthDate, LocalDate.now()).getYears();
    }
}