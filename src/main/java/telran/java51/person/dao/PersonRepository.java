package telran.java51.person.dao;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.data.repository.CrudRepository;

import telran.java51.person.model.Person;


public interface PersonRepository extends CrudRepository<Person, Integer> {

	Stream<Person> findByBirthDateBetween(LocalDate from, LocalDate to);

	Stream<Person> findByNameIgnoreCase(String name);

	Stream<Person> findByAddressCity(String city);


}
