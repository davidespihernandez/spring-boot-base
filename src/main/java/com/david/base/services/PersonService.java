package com.david.base.services;

import com.david.base.dtos.PersonDto;
import com.david.base.entities.Person;
import com.david.base.exceptions.PersonNotFoundException;
import com.david.base.mappers.PersonMapper;
import com.david.base.repositories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private PersonRepository personRepository;
    
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    
    public List<Person> searchPeople(String filter) {
        return personRepository.search(filter);
    }
    
    public Person getPerson(Long personId) {
        return personRepository.getOne(personId);
    }
    
    @Transactional
    public Person createPerson(PersonDto personDto) {
        Person person = PersonMapper.INSTANCE.personDtoToPerson(personDto);
        return personRepository.save(person);
    }
    
    @Transactional
    public Person updatePerson(Long personId, PersonDto personDto) {
        Optional<Person> maybePerson = personRepository.findById(personId);
        if (!maybePerson.isPresent()) {
            throw new PersonNotFoundException();
        }
        Person person = maybePerson.get();
        PersonMapper.INSTANCE.updatePersonFromDto(personDto, person);
        return personRepository.save(person);
    }
    
    @Transactional
    public void deletePerson(Long personId) {
        Optional<Person> person = personRepository.findById(personId);
        if (!person.isPresent()) {
            throw new PersonNotFoundException();
        }
        personRepository.delete(person.get());
    }
    
    public Page<Person> searchPeoplePaginated(int page, int size) {
        System.out.println("New thing");
        return personRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Order.asc("firstName"))));
    }
}
