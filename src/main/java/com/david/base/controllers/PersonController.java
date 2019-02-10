package com.david.base.controllers;

import com.david.base.dtos.PersonDto;
import com.david.base.entities.Person;
import com.david.base.mappers.PersonMapper;
import com.david.base.services.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PersonController {
    
    private PersonService personService;
    
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    @GetMapping("people")
    List<PersonDto> getPeople(@RequestParam(value = "filter", required = false) String filter) {
        return personService.searchPeople(Optional.ofNullable(filter).orElse(""))
                .stream()
                .map(PersonMapper.INSTANCE::personToPersonDto)
                .collect(Collectors.toList());
    }
    
    @GetMapping(path = "person/{id}")
    PersonDto getPerson(@PathVariable(name = "id") Long personId) {
        return PersonMapper.INSTANCE.personToPersonDto(personService.getPerson(personId));
    }
    
    @PostMapping("people")
    PersonDto createPerson(@RequestBody PersonDto personDto) {
        return PersonMapper.INSTANCE.personToPersonDto(personService.createPerson(personDto));
    }
    
    @PutMapping(path = "person/{id}")
    PersonDto modifyPerson(@PathVariable(name = "id") Long personId, @RequestBody PersonDto personDto) {
        return PersonMapper.INSTANCE.personToPersonDto(personService.updatePerson(personId, personDto));
    }
    
    @DeleteMapping(path = "person/{id}")
    void deletePerson(@PathVariable(name = "id") Long personId) {
        personService.deletePerson(personId);
    }
    
    @GetMapping(path = "paginated", params = { "page", "size" })
    Page<PersonDto> getPeoplePaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        return personService.searchPeoplePaginated(page, size).map(PersonMapper.INSTANCE::personToPersonDto);
    }
}
