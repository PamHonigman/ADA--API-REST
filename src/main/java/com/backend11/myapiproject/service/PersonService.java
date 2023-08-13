package com.backend11.myapiproject.service;

import com.backend11.myapiproject.dto.PersonDTO;
import com.backend11.myapiproject.entity.Person;
import com.backend11.myapiproject.exceptions.ExistingResourceException;
import com.backend11.myapiproject.exceptions.ResourceNotFoundException;
import com.backend11.myapiproject.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final PersonRepository personRepository;
    private final AcademicDegreeService academicDegreeService;

    public PersonService(PersonRepository personRepository, AcademicDegreeService academicDegreeService) {
        this.personRepository = personRepository;
        this.academicDegreeService = academicDegreeService;
    }

    public PersonDTO create(PersonDTO personDTO) {
        Person person = mapToEntity(personDTO);
        checkForExistingPerson(person.getId());
        person = personRepository.save(person);
        if(!CollectionUtils.isEmpty(personDTO.getAcademicDegreeDTOS())) {
            academicDegreeService.create(personDTO.getAcademicDegreeDTOS(), person);
        }

        return personDTO;
    }

    public List<PersonDTO> retrieveAll(){
        List<Person> persons = personRepository.findAll();
        if (persons.isEmpty()){
            throw new ResourceNotFoundException("Los recursos que se está intentando consultar no existen");
        }
        /*List<PersonDTO> personDTOS = new ArrayList<>();

        for (Person person : persons) {
            PersonDTO personDTO = mapToDTO(person);
            personDTOS.add(personDTO);
        }

        return personDTOS;*/
        return persons.stream()
                .map(person -> mapToDTO(person))
                .collect(Collectors.toList());
    }

    public PersonDTO retrieveById(String id) {
        Optional<Person> person = personRepository.findById(id);

        if (person.isEmpty()){
            throw new ResourceNotFoundException();
        }

        return mapToDTO(person.get());
    }

    public void delete(String personId) {
        if (!personRepository.existsById(personId)){
            throw new ResourceNotFoundException("El recurso que se está intentando eliminar no existe");
        }
        personRepository.deleteById(personId);
    }

    public void replace(String personId, PersonDTO personDTO) {
        Optional<Person> person = personRepository.findById(personId);
        if (person.isEmpty()){
            throw new ResourceNotFoundException();
        }
        Person personToReplace = person.get();
        personToReplace.setName(personDTO.getName());
        personToReplace.setLastName(personDTO.getLastName());
        personToReplace.setBirthday(LocalDate.parse(personDTO.getBirthday(), DATE_TIME_FORMATTER));
        personToReplace.setGender(personDTO.getGender());
        personToReplace.setCivilStatus(personDTO.getCivilStatus());

        personRepository.save(personToReplace);
    }

    public void modify(String personId, Map<String, Object> fieldsToModify) {
        Optional<Person> person = personRepository.findById(personId);
        if (person.isEmpty()){
            throw new ResourceNotFoundException();
        }
        Person personToModify = person.get();
        fieldsToModify.forEach((key, value) -> personToModify.modifyAttributeValue(key, value));
        personRepository.save(personToModify);
    }

    private PersonDTO mapToDTO(Person person) {
        PersonDTO personDTO = new PersonDTO(person.getId(), person.getName(), person.getLastName(),
                                            person.getBirthday().toString(), person.getGender(), person.getCivilStatus(),
                                            academicDegreeService.mapToDTOS(person.getAcademicDegrees()));

        return personDTO;
    }

    private void checkForExistingPerson(String personId) {
        if (personRepository.existsById(personId)){
            throw new ExistingResourceException();
        }
    }

    private Person mapToEntity(PersonDTO personDTO){
        Person person = new Person(personDTO.getId(), personDTO.getName(), personDTO.getLastName(),
                LocalDate.parse(personDTO.getBirthday(), DATE_TIME_FORMATTER),
                personDTO.getGender(), personDTO.getCivilStatus());

        return person;
    }
}
