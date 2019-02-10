package com.david.base;

import com.david.base.dtos.PersonDto;
import com.david.base.entities.Person;
import com.david.base.factory.PersonFactory;
import com.david.base.services.PersonService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseApplication.class)
@WebAppConfiguration
@Transactional
public class PersonControllerTests {

	private static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(),
			Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	PersonFactory personFactory;
	
	@Autowired
	PersonService personService;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	void assertDtoEqualsObject(PersonDto personDto, Person person) {
		assertThat(personDto.getId()).isEqualTo(person.getId());
		assertThat(personDto.getFirstName()).isEqualTo(person.getFirstName());
		assertThat(personDto.getLastName()).isEqualTo(person.getLastName());
		assertThat(personDto.getPhone()).isEqualTo(person.getPhone());
	}
	
	@Test
	public void getPerson() throws Exception {
		Person person = personFactory.create();
		MvcResult mvcResult = mockMvc.perform(get("/person/" + person.getId()))
				.andExpect(status().isOk())
				.andReturn();
		PersonDto personDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), PersonDto.class);
		assertDtoEqualsObject(personDto, person);
	}

	@Test
	public void createPerson() throws Exception {
		PersonDto personDto = PersonFactory.createDto();
		MvcResult mvcResult = mockMvc.perform(post("/people/")
				.content(mapper.writeValueAsString(personDto))
				.contentType(contentType))
				.andExpect(status().isOk())
				.andReturn();
		PersonDto returned = mapper.readValue(mvcResult.getResponse().getContentAsString(), PersonDto.class);
		assertThat(returned.getId()).isNotNull();
		Person person = personService.getPerson(returned.getId());
		assertDtoEqualsObject(returned, person);
	}

	@Test
	public void updatePerson() throws Exception {
		Person person = personFactory.create();
		PersonDto personDto = PersonFactory.createDto(person);
		String newFirstName = "New " + UUID.randomUUID().toString();
		personDto.setFirstName(newFirstName);
		MvcResult mvcResult = mockMvc.perform(put("/person/" + person.getId().toString())
				.content(mapper.writeValueAsString(personDto))
				.contentType(contentType))
				.andExpect(status().isOk())
				.andReturn();
		PersonDto returned = mapper.readValue(mvcResult.getResponse().getContentAsString(), PersonDto.class);
		Person retrieved = personService.getPerson(returned.getId());
		assertDtoEqualsObject(personDto, retrieved);
	}

	@Test
	public void firstNameMustBeNotNull() throws Exception {
		PersonDto personDto = PersonFactory.createDto();
		personDto.setFirstName(null);
		mockMvc.perform(post("/people/")
				.content(mapper.writeValueAsString(personDto))
				.contentType(contentType))
				.andExpect(status().isNotAcceptable());
	}
}
