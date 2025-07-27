package dev.binarycoders.restpostgres.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.binarycoders.restpostgres.model.Person;
import dev.binarycoders.restpostgres.repository.PersonRepository;
import dev.binarycoders.restpostgres.repository.entity.PersonEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static dev.binarycoders.restpostgres.model.definition.ContactType.EMAIL;
import static dev.binarycoders.restpostgres.model.definition.Gender.MALE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void createPerson() throws Exception {
        final var person = new Person();
        person.setName("John");
        person.setSurname("Doe");
        person.setGender(MALE);
        person.setBirthDate(LocalDate.of(1990, 1, 1));
        person.setContactType(EMAIL);
        person.setContactValue("john.doe@example.org");

        mockMvc.perform(post("/api/v1/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.birthDate").value("1990/01/01"))
                .andExpect(jsonPath("$.contactType").value("EMAIL"))
                .andExpect(jsonPath("$.contactValue").value("john.doe@example.org"));
    }

    @Test
    void findPersonById() throws Exception {
        final var personId = createAPerson();

        mockMvc.perform(get("/api/v1/people/{id}", personId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findAllActivePeople() throws Exception {
        mockMvc.perform(get("/api/v1/people"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updatePerson() throws Exception {
        final var personId = createAPerson();
        final var person = new Person();
        person.setId(personId);
        person.setName("Updated John");
        person.setSurname("Doe");
        person.setGender(MALE);
        person.setBirthDate(LocalDate.of(1990, 1, 1));
        person.setContactType(EMAIL);
        person.setContactValue("john.doe@example.org");

        mockMvc.perform(put("/api/v1/people/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.birthDate").value("1990/01/01"))
                .andExpect(jsonPath("$.contactType").value("EMAIL"))
                .andExpect(jsonPath("$.contactValue").value("john.doe@example.org"));
    }

    @Test
    void deletePerson() throws Exception {
        final var personId = createAPerson();

        mockMvc.perform(delete("/api/v1/people/{id}", personId))
                .andExpect(status().isNoContent());
    }

    private Long createAPerson() {
        var personEntity = new PersonEntity();
        personEntity.setName("John");
        personEntity.setSurname("Doe");
        personEntity.setGender(MALE);
        personEntity.setBirthDate(LocalDate.of(1990, 1, 1));
        personEntity.setContactType(EMAIL);
        personEntity.setContactValue("john.doe@example.org");

        return personRepository.save(personEntity).getId();
    }
}