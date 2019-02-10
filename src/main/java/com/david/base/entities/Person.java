package com.david.base.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private String firstName;
    
    @NotNull
    @Column(nullable = false)
    private String lastName;
    
    private String phone;
    
    public String getFullName() {
        return Optional.ofNullable(firstName)
                .map(firstName -> firstName + " ")
                .orElse("") +
                Optional.ofNullable(lastName)
                        .orElse("");
    }
}
