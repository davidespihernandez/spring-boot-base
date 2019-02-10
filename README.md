# spring-boot-base
Base Spring Boot REST application

This project can serve as a base for a REST backend in Spring Boot.
In this project you can find examples of:

### Lombok
Removes boilerplate code adding getters, setters, hashcode, equals... automatically to the classes.
### Mapstruct
Allows to create DTOs easily.
### JPA with Postgres
A sample entity has been created.
### Entity factory for testing purposes
Utility classes to create rows for integration tests.
### REST endpoints
Using a mapper created with Mapstruct
For instance, this is an endpoint that retrieves paginated data on the "Person" entity/table:

```
    @GetMapping(path = "paginated", params = { "page", "size" })
    Page<PersonDto> getPeoplePaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        return personService.searchPeoplePaginated(page, size).map(PersonMapper.INSTANCE::personToPersonDto);
    }
```

The call returns something like:

```
{
    "content": [
        {
            "id": 1,
            "firstName": "Cambio",
            "lastName": "De Nombre",
            "fullName": "Cambio De Nombre",
            "phone": "7654321"
        },
        {
            "id": 2,
            "firstName": "Lirios",
            "lastName": "Espí",
            "fullName": "Lirios Espí",
            "phone": "123434352"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "offset": 0,
        "pageSize": 2,
        "pageNumber": 0,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 2,
    "number": 0,
    "first": true,
    "numberOfElements": 2,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "empty": false
}
```

A Postman collection to test can be found on the root folder.

### Flyway as the database migration system
In order to generate migrations: 
- Change the property spring.jpa.hibernate.ddl-auto to update
- Change spring.flyway.enabled to false
- Run the application and copy the generated SQL from the log
- Create a new file in the resources/db/migration folder, named properly (VYYYYMMDD__name.sql) and paste the generated sql there
- Change the property spring.jpa.hibernate.ddl-auto to none
- Change spring.flyway.enabled to true
- Execute the maven plugin flyway clean target
- When the application starts, all migrations will be applied.

### Integration tests
Tests for the REST controller can be found. A simple test is something like:
```
	@Test
	public void getPerson() throws Exception {
		Person person = personFactory.create();
		MvcResult mvcResult = mockMvc.perform(get("/person/" + person.getId()))
				.andExpect(status().isOk())
				.andReturn();
		PersonDto personDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), PersonDto.class);
		assertDtoEqualsObject(personDto, person);
	}
```
