package com.david.base.repositories;

import com.david.base.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findById(Long id);
    @Query("from Person where lower(firstName) like concat('%', ?1, '%') or lower(lastName) like concat('%', ?1, '%') order by firstName")
    List<Person> search(String query);
}
