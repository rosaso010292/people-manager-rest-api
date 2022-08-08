package com.api.people.manager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.api.people.manager.controller.PersonController;
import com.api.people.manager.model.Person;
import com.api.people.manager.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PersonController.class)
public class PersonServiceTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PersonService personService;
	private static String URL = "/public/person";

	@Test
	public void createPersonTest() throws Exception {
		Person person = Person.builder().idPerson(1L).age(new Date()).name("Omar").lastName("Rosas").build();

		when(personService.save(any(Person.class))).thenReturn((person));

		mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(mapToJson(person)))
				.andExpect(status().isCreated()).andDo(print());
	}

	@Test
	public void createPersonBadRequestTest() throws Exception {
		Person person = Person.builder().idPerson(1L).age(new Date()).name("").lastName("Rosas").build();

		when(personService.save(any(Person.class))).thenReturn((person));

		mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(mapToJson(person)))
				.andExpect(status().isBadRequest()).andDo(print());
	}

	@Test
	public void getPersonByIdSuccessTest() throws Exception {
		Person person = Person.builder().idPerson(1L).age(new Date()).name("Omar").lastName("Rosas").build();

		when(personService.findById(1L)).thenReturn(Optional.of(person));

		mockMvc.perform(MockMvcRequestBuilders.get(URL + "/{id}", 1).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.idPerson").value(1));
	}

	@Test
	public void getPersonByIdNotFoundTest() throws Exception {
		when(personService.findById(1L)).thenReturn(Optional.empty());
		mockMvc.perform(get(URL + "/{id}", 1L)).andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	public void updatePersonSuccessTest() throws Exception {
		Person person = Person.builder().idPerson(1L).age(new Date()).name("Omar").lastName("Rosas").build();
		Person updatedPerson = Person.builder().idPerson(1L).age(new Date()).name("Jacinto").lastName("Rosas").build();

		when(personService.findById(1L)).thenReturn(Optional.of(person));
		when(personService.update(any(Person.class), any(Person.class))).thenReturn(updatedPerson);

		mockMvc.perform(
				put(URL + "/{id}", 1L).contentType(MediaType.APPLICATION_JSON).content(mapToJson(updatedPerson)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.idPerson").value(updatedPerson.getIdPerson()))
				.andExpect(jsonPath("$.age").value(new SimpleDateFormat("yyyy-MM-dd").format(updatedPerson.getAge())))
				.andExpect(jsonPath("$.name").value(updatedPerson.getName()))
				.andExpect(jsonPath("$.lastName").value(updatedPerson.getLastName())).andDo(print());
	}

	@Test
	public void updatePersonNotFoundTest() throws Exception {
		Person updatedPerson = Person.builder().idPerson(1L).age(new Date()).name("Jacinto").lastName("Rosas").build();

		when(personService.findById(1L)).thenReturn(Optional.empty());
		when(personService.save(any(Person.class))).thenReturn(updatedPerson);

		mockMvc.perform(
				put(URL + "/{id}", 1L).contentType(MediaType.APPLICATION_JSON).content(mapToJson(updatedPerson)))
				.andExpect(status().isNotFound()).andDo(print());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
