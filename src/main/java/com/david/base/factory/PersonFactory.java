package com.david.base.factory;

import com.david.base.dtos.PersonDto;
import com.david.base.entities.Person;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PersonFactory extends EntityFactory<Person> {
    
    @Override
    public Person build() {
        return new Person();
    }
    
    public Person create() {
        Person person = build();
        create(person, p -> {
            p.setFirstName(UUID.randomUUID().toString());
            p.setLastName(UUID.randomUUID().toString());
        });
        return person;
    }
    
    public Person createWithFirstNameAndLastName(String firstName, String lastName) {
        Person person = build();
        create(person, p -> {
            p.setFirstName(firstName);
            p.setLastName(lastName);
        });
        return person;
    }
    
    public static PersonDto createDto() {
        PersonDto personDto = new PersonDto();
        personDto.setFirstName(UUID.randomUUID().toString());
        personDto.setLastName(UUID.randomUUID().toString());
        return personDto;
    }
    
    public static PersonDto createDto(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.setId(person.getId());
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setPhone(person.getPhone());
        return personDto;
    }
    
}
