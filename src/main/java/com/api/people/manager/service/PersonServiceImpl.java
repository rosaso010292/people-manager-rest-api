package com.api.people.manager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.people.manager.model.Person;
import com.api.people.manager.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {
	@Autowired
	private PersonRepository repository;

	@Override
	@Transactional
	public Person save(Person person) {
		return repository.save(person);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Person> findById(Long idPerson) {
		return repository.findById(idPerson);
	}

	@Override
	@Transactional
	public Person update(Person person, Person personDb) {
		personDb.setName(person.getName());
		personDb.setLastName(person.getLastName());
		personDb.setAge(person.getAge());
		return repository.save(personDb);
	}

}
