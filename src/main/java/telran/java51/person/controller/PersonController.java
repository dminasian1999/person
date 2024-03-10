package telran.java51.person.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.ChildDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.EmployeeDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
	final PersonService personService;

	@PostMapping
	public Boolean addPerson(@RequestBody PersonDto personDto) {
		return personService.addPerson(personDto);
	}

	@GetMapping("/{id}")
	public PersonDto findPersonById(@PathVariable Integer id) {
		return personService.findPersonById(id);
	}

	@GetMapping("/city/{city}")
	public List<PersonDto> findPersonByCity(@PathVariable String city) {
		return personService.findPersonByCity(city);
	}

	@GetMapping("/ages/{from}/{to}")
	public List<PersonDto> findPersonByAges(@PathVariable Integer from, @PathVariable Integer to) {
		return personService.findPersonByAges(from, to);
	}

	@PutMapping("/{id}/name/{name}")
	public PersonDto updateName(@PathVariable Integer id, @PathVariable String name) {
		return personService.updateName(id, name);
	}

	@GetMapping("/name/{name}")
	public List<PersonDto> findPersonByName(@PathVariable String name) {
		return personService.findPersonByName(name);
	}

	@PutMapping("/{id}/address")
	public PersonDto updateAddress(@PathVariable Integer id, @RequestBody AddressDto address) {
		return personService.updateAddress(id, address);
	}

	@DeleteMapping("/{id}")
	public PersonDto deletePerson(@PathVariable Integer id) {
		return personService.deletePerson(id);
	}

	@GetMapping("/population/city")
	public List<CityPopulationDto> getCitiesPopulation() {
		return personService.getCityPopulation();
	}

	@GetMapping("/children")
	public List<ChildDto> findAllChildren() {
		return personService.findAllChildren();
	}
	
	@GetMapping("/salary/{from}/{to}")
	public List<EmployeeDto> findEmployeesBySalary(@PathVariable Integer from, @PathVariable Integer to) {
		return personService.findEmployeesBySalary(from,to);
	}
}
