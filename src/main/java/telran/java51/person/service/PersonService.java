package telran.java51.person.service;

import java.util.List;

import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.ChildDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.EmployeeDto;
import telran.java51.person.dto.PersonDto;

public interface PersonService {
	Boolean addPerson(PersonDto personDto);

	PersonDto findPersonById(Integer id);

	Iterable<PersonDto> findPersonByCity(String city);

	Iterable<PersonDto> findPersonByAges(Integer from, Integer to);
	
	PersonDto updateName(Integer id, String name);
	
	Iterable<PersonDto> findPersonByName(String name);
	
	PersonDto updateAddress(Integer id, AddressDto address);
	
	PersonDto deletePerson(Integer id);

	Iterable<CityPopulationDto> getCityPopulation();
	
	Iterable<ChildDto> findAllChildren();

	Iterable<EmployeeDto> findEmployeesBySalary(int from, int to);
}
