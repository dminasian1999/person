package telran.java51.person.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.model.Person;


public interface PersonRepository extends CrudRepository<Person, Integer> {
	Stream<Person> findByBirthDateBetween(LocalDate from, LocalDate to);

	Stream<Person> findByNameIgnoreCase(String name);

	Stream<Person> findByAddressCity(String city);

	@Query("select new telran.java51.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city order by count(p) desc")
	List<CityPopulationDto> getCitiesPopulation();	
	
}
