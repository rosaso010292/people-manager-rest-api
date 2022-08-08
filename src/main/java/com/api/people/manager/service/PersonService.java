package com.api.people.manager.service;

import java.util.Optional;

import com.api.people.manager.model.Person;

public interface PersonService {
	public Person save(Person person);

	public Optional<Person> findById(Long idPerson);

	public Person update(Person person, Person personDb);
}
