package telran.java51.person.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.ChildDto;
import telran.java51.person.dto.CityPopulationDto;
import telran.java51.person.dto.EmployeeDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exceptions.PersonNotFoundException;
import telran.java51.person.model.Address;
import telran.java51.person.model.Child;
import telran.java51.person.model.Employee;
import telran.java51.person.model.Person;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public Boolean addPerson(PersonDto personDto) {
		if (personRepository.existsById(personDto.getId()) ) {
			return false;
		}
		if (personDto.getClass() == PersonDto.class) {
			personRepository.save(modelMapper.map(personDto, Person.class));
		}
		if (personDto.getClass() == EmployeeDto.class) {
			personRepository.save(modelMapper.map(personDto, Employee.class));
		} 
		if (personDto.getClass() == ChildDto.class) {
			personRepository.save(modelMapper.map(personDto, Child.class));
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return personCheck(person);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PersonDto> findPersonByCity(String city) {
			return personRepository.findByAddressCity(city)
								.map(p ->  personCheck(p))
								.collect(Collectors.toList());
	}
	
	
	private PersonDto personCheck(Person person) {
		if (person instanceof Child child) {
			return modelMapper.map(child, ChildDto.class);
		}
		if (person instanceof Employee employee) {
			return modelMapper.map(employee, EmployeeDto.class);
		}
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	@Transactional(readOnly = true)

	public List<PersonDto> findPersonByAges(Integer from, Integer to) {
		LocalDate fromL = LocalDate.now().minusYears(from.longValue());
		LocalDate toL = LocalDate.now().minusYears(to.longValue());
		return personRepository.findByBirthDateBetween(toL, fromL)
				.map(p -> personCheck(p))
				.toList();
	}
	@Override
	@Transactional(readOnly = true)
	public List<PersonDto> findPersonByName(String name) {
		return personRepository.findByNameIgnoreCase(name)
				.map(p -> personCheck(p))
				.toList();
	}

	@Override
	@Transactional
	public PersonDto updateName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
		return personCheck(person);
	}

	

	@Override
	@Transactional
	public PersonDto updateAddress(Integer id, AddressDto address) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setAddress(modelMapper.map(address, Address.class));
		return personCheck(person);
	}

	@Override
	@Transactional
	public PersonDto deletePerson(Integer id) {
		PersonDto personDto = findPersonById(id);
		personRepository.deleteById(id);
		return personDto;
	}

	@Override
	public List<CityPopulationDto> getCityPopulation() {
		return personRepository.getCitiesPopulation();
	}
	
	@Override
	public List<ChildDto> findAllChildren() {
		return StreamSupport.stream(personRepository.findAll().spliterator(),false)
							.filter(p->p instanceof Child child)
							.map(ch-> modelMapper.map(ch, ChildDto.class))
							.toList();
	}

	@Override
	public List<EmployeeDto> findEmployeesBySalary(int from, int to) {
		return StreamSupport.stream(personRepository.findAll().spliterator(),false)
				.filter(p-> p instanceof Employee employee)
				.map(e-> modelMapper.map(e, EmployeeDto.class))
				.filter(e-> (e.getSalary() >= from && e.getSalary() <= to))
				.toList();
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if (personRepository.count() == 0) {
			Person person = new Person(1000, "John", LocalDate.of(1985, 3, 11),
					new Address("Tel-aviv", "Ben Gurion", 81));
			Child child = new Child(2000, "Moshe", LocalDate.of(2018, 7, 5), new Address("Ashkelon", "Bar Kohva", 21),
					"Shalom");
			Employee employee = new Employee(3000, "Sara", LocalDate.of(1995, 11, 23),
					new Address("Rehovot", "Hertz", 7), "Motorola", 20_000);
			personRepository.save(person);
			personRepository.save(child);
			personRepository.save(employee);
		}
	}

	

}
