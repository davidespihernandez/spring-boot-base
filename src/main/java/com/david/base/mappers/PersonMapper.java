package com.david.base.mappers;

import com.david.base.dtos.PersonDto;
import com.david.base.entities.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    
    PersonDto personToPersonDto(Person person);
    
    @Mapping(target = "id", ignore = true)
    Person personDtoToPerson(PersonDto personDto);
    
    @Mapping(target = "id", ignore = true)
    void updatePersonFromDto(PersonDto personDto, @MappingTarget Person person);
}
