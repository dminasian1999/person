package telran.java51.person.service;

import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.CityPopulationDto;
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

}
