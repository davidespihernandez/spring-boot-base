package com.david.base.mappers;

import com.david.base.dtos.PersonDto;
import com.david.base.entities.Person;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class PersonMapperTest {
    @Test
    public void shouldMapPersonToPersonDto() {
        // given a person
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("first");
        person.setLastName("last");
        person.setPhone("+34555666555");
        // when
        PersonDto personDto = PersonMapper.INSTANCE.personToPersonDto(person);
        // then all fields are in the DTO
        assertThat(personDto).isNotNull();
        assertThat(personDto.getId()).isEqualTo(person.getId());
        assertThat(personDto.getFirstName()).isEqualTo(person.getFirstName());
        assertThat(personDto.getLastName()).isEqualTo(person.getLastName());
        assertThat(personDto.getFullName()).isEqualTo(person.getFullName());
    }
}
