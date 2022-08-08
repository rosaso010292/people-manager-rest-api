package com.api.people.manager;

import java.util.Date;

import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.api.people.manager.model.Person;
import com.api.people.manager.repository.PersonRepository;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryTest {
	@Autowired
	private PersonRepository repository;

	@Test
	@Order(1)
	@Rollback(value = false)
	public void savePersonTest() {
		Person person = Person.builder().age(new Date()).name("Omar").lastName("Rosas").build();
		repository.save(person);
		Assertions.assertThat(person.getIdPerson()).isPositive();
	}

	@Test
	@Order(2)
	public void getPersonByIdTest() {
		Person person = repository.findById(1L).get();
		Assertions.assertThat(person.getIdPerson()).isEqualTo(1L);

	}

	@Test
	@Order(3)
	@Rollback(value = false)
	public void updatePersonTest() {
		Person person = repository.findById(1L).get();
		person.setName("Juan");
		Person personUpdate = repository.save(person);
		Assertions.assertThat(personUpdate.getName()).isEqualTo("Juan");

	}

}
