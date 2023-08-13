package com.backend11.myapiproject.service;

import com.backend11.myapiproject.dto.AcademicDegreeDTO;
import com.backend11.myapiproject.entity.AcademicDegree;
import com.backend11.myapiproject.entity.Person;
import com.backend11.myapiproject.exceptions.ResourceNotFoundException;
import com.backend11.myapiproject.repository.AcademicDegreeRepository;
import com.backend11.myapiproject.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AcademicDegreeService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final AcademicDegreeRepository academicDegreeRepository;
    private final PersonRepository personRepository;

    public AcademicDegreeService(AcademicDegreeRepository academicDegreeRepository, PersonRepository personRepository) {
        this.academicDegreeRepository = academicDegreeRepository;
        this.personRepository = personRepository;
    }

    public void create(List<AcademicDegreeDTO> academicDegreeDTOS, Person person){
        List<AcademicDegree> academicDegrees = academicDegreeDTOS.stream()
                .map(academicDegreeDTO -> mapToEntity(academicDegreeDTO, person))
                .collect(Collectors.toList());

        academicDegreeRepository.saveAll(academicDegrees);

    }

    public AcademicDegreeDTO retrieveById(String personId, Integer academicDegreeId) {
        if(!personRepository.existsById(personId)){
            throw new ResourceNotFoundException("La persona no existe.");
        }
        Optional<AcademicDegree> academicDegree = academicDegreeRepository.findById(academicDegreeId);
        if (academicDegree.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return mapToDTO(academicDegree.get());
    }

    public void create(AcademicDegreeDTO academicDegreeDTO, String personId){
        Optional<Person> existingPerson = personRepository.findById(personId);
        if (existingPerson.isEmpty()){
            throw new ResourceNotFoundException("La persona a la que se está intentando asociar no existe");
        }
        AcademicDegree academicDegree = mapToEntity(academicDegreeDTO, existingPerson.get());
        academicDegree = academicDegreeRepository.save(academicDegree);
        academicDegreeDTO.setId(academicDegree.getId());
    }

    public void delete(Integer academicDegreeId) {
        if (!academicDegreeRepository.existsById(academicDegreeId)){
            throw new ResourceNotFoundException("El recurso que se está intentando eliminar no existe");
        }
        academicDegreeRepository.deleteById(academicDegreeId);
    }

    public List<AcademicDegreeDTO> retrieveAll() {
        List<AcademicDegree> academicDegrees = academicDegreeRepository.findAll();

        if (academicDegrees.isEmpty()){
            throw new ResourceNotFoundException("Los recursos que se está intentando consultar no existen");
        }
        return academicDegrees.stream()
                .map(academicDegree -> mapToDTO(academicDegree))
                .collect(Collectors.toList());
    }

    public void replace(String personId, Integer academicDegreeId, AcademicDegreeDTO academicDegreeDTO) {
        if(!personRepository.existsById(personId)){
            throw new ResourceNotFoundException("La persona no existe.");
        }
        Optional<AcademicDegree> academicDegree = academicDegreeRepository.findById(academicDegreeId);
        if (academicDegree.isEmpty()){
            throw new ResourceNotFoundException();
        }
        AcademicDegree academicDegreeToReplace = academicDegree.get();
        academicDegreeToReplace.setTitle(academicDegreeDTO.getTitle());
        academicDegreeToReplace.setInstitution(academicDegreeDTO.getInstitution());
        academicDegreeToReplace.setDate(LocalDate.parse(academicDegreeDTO.getFecha(), DATE_TIME_FORMATTER));

        academicDegreeRepository.save(academicDegreeToReplace);
    }

    public void modify(String personId, Integer academicDegreeId, Map<String, Object> fieldsToModify) {
        if(!personRepository.existsById(personId)){
            throw new ResourceNotFoundException("La persona no existe.");
        }
        Optional<AcademicDegree> academicDegree = academicDegreeRepository.findById(academicDegreeId);
        if (academicDegree.isEmpty()){
            throw new ResourceNotFoundException();
        }
        AcademicDegree academicDegreeToModify = academicDegree.get();
        fieldsToModify.forEach((key, value) -> academicDegreeToModify.modifyAttributeValue(key, value));
        academicDegreeRepository.save(academicDegreeToModify);
    }

    private AcademicDegree mapToEntity(AcademicDegreeDTO academicDegreeDTO, Person person) {
        AcademicDegree academicDegree = new AcademicDegree(academicDegreeDTO.getTitle(),
                academicDegreeDTO.getInstitution(), LocalDate.parse(academicDegreeDTO.getFecha(),
                DATE_TIME_FORMATTER), person);

        return academicDegree;
    }


    public List<AcademicDegreeDTO> mapToDTOS(List<AcademicDegree> academicDegrees) {

        return academicDegrees.stream()
                .map(academicDegree -> mapToDTO(academicDegree))
                .collect(Collectors.toList());
    }

    private AcademicDegreeDTO mapToDTO(AcademicDegree academicDegree) {
        AcademicDegreeDTO academicDegreeDTO = new AcademicDegreeDTO(academicDegree.getTitle(), academicDegree.getInstitution(),
                academicDegree.getDate().toString());
        academicDegreeDTO.setId(academicDegree.getId());

        return academicDegreeDTO;
    }
}
