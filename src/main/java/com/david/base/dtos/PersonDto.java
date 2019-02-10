package com.david.base.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDto {
    private long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;
}
