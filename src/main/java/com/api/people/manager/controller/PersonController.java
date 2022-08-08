package com.api.people.manager.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.people.manager.dto.PersonDTO;
import com.api.people.manager.exceptions.ResourceNotFoundException;
import com.api.people.manager.model.Person;
import com.api.people.manager.service.PersonService;
import com.api.people.manager.util.Constants;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/public/person")
@Log4j
public class PersonController {
	@Autowired
	private PersonService service;

	@ApiOperation(value = "Create a New Person", notes = "Operation Used to Create the Data of a New Person")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request - The Person Information is Incorrect"),
			@ApiResponse(code = 201, message = "Created - Successfully Created Person", response = PersonDTO.class) })
	@PostMapping
	public ResponseEntity<Person> createPerson(@RequestBody @Valid PersonDTO personDTO) {
		log.info("Starting Creating Person....");
		log.info("Request: " + personDTO.toString());
		Person person = Person.builder().age(personDTO.getAge()).lastName(personDTO.getLastName())
				.name(personDTO.getName()).build();
		person = service.save(person);
		log.info(Constants.RESPONSE + person.toString());
		log.info("Ending Creating Person....");
		return ResponseEntity.status(HttpStatus.CREATED).body(person);
	}

	@ApiOperation(value = "Get the Person by Id", notes = "Operation Used to Get the Data of a Specific Person")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not Found - The Person does not Exist"),
			@ApiResponse(code = 200, message = "0K - Found Person", response = PersonDTO.class) })
	@GetMapping("/{idPerson}")
	public ResponseEntity<Person> getPersonById(@PathVariable Long idPerson) throws ResourceNotFoundException {
		log.info("Starting Fetching the Person By Id....");
		log.info("Person Id: " + idPerson);
		Optional<Person> opPerson = service.findById(idPerson);
		if (!opPerson.isPresent()) {
			throw new ResourceNotFoundException("Person Not Found With Id: " + idPerson);
		}
		Person person = opPerson.get();
		log.info(Constants.RESPONSE + person.toString());
		log.info("Ending Fetching the Person By Id....");
		return ResponseEntity.ok().body(person);
	}

	@ApiOperation(value = "Update a Person", notes = "Operation Used to Update the Data of a Person")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Bad Request - The Person Information is Incorrect"),
			@ApiResponse(code = 200, message = "OK - Successfully Update Person", response = PersonDTO.class) })
	@PutMapping("/{idPerson}")
	public ResponseEntity<Person> updatePerson(@RequestBody @Valid PersonDTO personDTO, @PathVariable Long idPerson)
			throws ResourceNotFoundException {
		log.info("Starting Updating Person....");
		log.info("Request: " + personDTO.toString());
		log.info("Person Id: " + idPerson);
		Optional<Person> opPerson = service.findById(idPerson);
		if (!opPerson.isPresent()) {
			throw new ResourceNotFoundException("Person Not Found With Id: " + idPerson);
		}
		Person person = Person.builder().age(personDTO.getAge()).lastName(personDTO.getLastName())
				.name(personDTO.getName()).build();
		person = service.update(person, opPerson.get());
		log.info(Constants.RESPONSE + person.toString());
		log.info("Ending Updating Person....");
		return ResponseEntity.status(HttpStatus.OK).body(person);
	}

}
