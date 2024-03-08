package telran.java51.person.service;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import telran.java51.person.dao.PersonRepository;
import telran.java51.person.dto.AddressDto;
import telran.java51.person.dto.PersonDto;
import telran.java51.person.dto.exceptions.PersonNotFoundException;
import telran.java51.person.model.Address;
import telran.java51.person.model.Person;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonServiceImpl implements PersonService {

	final PersonRepository personRepository;
	final ModelMapper modelMapper;
	
	@Override
	public Boolean addPerson(PersonDto personDto) {
		if(personRepository.existsById(personDto.getId())) {
			return false;
		}
		personRepository.save(modelMapper.map(personDto, Person.class));
		return true;
	}

	@Override
	public PersonDto findPersonById(Integer id) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findPersonByCity(String city) {
		return personRepository.findByAddressCity(city)
				.map(p->modelMapper.map(p, PersonDto.class))
				.toList();
	}

	@Override
	public Iterable<PersonDto> findPersonByAges(Integer from, Integer to) {
		LocalDate fromL = LocalDate.now().minusYears(from.longValue());
		LocalDate toL = LocalDate.now().minusYears(to.longValue());
		return  personRepository.findByBirthDateBetween(toL,fromL)
								.map(p->modelMapper.map(p, PersonDto.class))
								.toList();
	}

	@Override
	public PersonDto updateName(Integer id, String name) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setName(name);
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public Iterable<PersonDto> findPersonByName(String name) {
		return personRepository.findByNameIgnoreCase(name)
				.map(p->modelMapper.map(p, PersonDto.class))
				.toList();
	}

	@Override
	public PersonDto updateAddress(Integer id, AddressDto address) {
		Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
		person.setAddress(modelMapper.map(address, Address.class));
		personRepository.save(person);
		return modelMapper.map(person, PersonDto.class);
	}

	@Override
	public PersonDto deletePerson(Integer id) {
		PersonDto personDto = findPersonById(id);
		personRepository.deleteById(id);
		return personDto;
	}

}
