package dev.binarycoders.restpostgres.controller.mapper;

import dev.binarycoders.restpostgres.model.Person;
import dev.binarycoders.restpostgres.repository.entity.PersonEntity;

public final class PersonMapper {

    private PersonMapper() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static Person toApi(final PersonEntity entity) {
        if (entity == null) {
            return null;
        }

        return Person.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .gender(entity.getGender())
                .birthDate(entity.getBirthDate())
                .contactType(entity.getContactType())
                .contactValue(entity.getContactValue())
                .build();
    }

    public static PersonEntity toEntity(final Person person) {
        if (person == null) {
            return null;
        }

        final var entity = new PersonEntity();

        entity.setName(person.getName());
        entity.setSurname(person.getSurname());
        entity.setGender(person.getGender());
        entity.setBirthDate(person.getBirthDate());
        entity.setContactType(person.getContactType());
        entity.setContactValue(person.getContactValue());

        return entity;
    }
}