package com.backend11.myapiproject.controller;

import com.backend11.myapiproject.dto.AcademicDegreeDTO;
import com.backend11.myapiproject.service.AcademicDegreeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("persons/{personId}/academic-degrees")
public class AcademicDegreeController {

    private final AcademicDegreeService academicDegreeService;

    public AcademicDegreeController(AcademicDegreeService academicDegreeService) {
        this.academicDegreeService = academicDegreeService;
    }

    @PostMapping
    public ResponseEntity createAcademicDegree(@PathVariable String personId,
                                               @RequestBody AcademicDegreeDTO academicDegreeDTO){
        academicDegreeService.create(academicDegreeDTO, personId);

        return new ResponseEntity(academicDegreeDTO.getId(), HttpStatus.CREATED );
    }

    @GetMapping
    public ResponseEntity retrieve(){
        return new ResponseEntity(academicDegreeService.retrieveAll(), HttpStatus.OK);
    }

    @GetMapping("/{academicDegreeId}")
    public ResponseEntity retrieveById(@PathVariable String personId,
                                       @PathVariable Integer academicDegreeId){
        AcademicDegreeDTO academicDegreeDTO = academicDegreeService.retrieveById(personId, academicDegreeId);

        return new ResponseEntity(academicDegreeDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{academicDegreeId}")
    public ResponseEntity delete(@PathVariable Integer academicDegreeId){
        academicDegreeService.delete(academicDegreeId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/{academicDegreeId}")
    public ResponseEntity replace(@PathVariable String personId,
                                  @PathVariable Integer academicDegreeId,
                                  @RequestBody AcademicDegreeDTO academicDegreeDTO) {

        academicDegreeService.replace(personId, academicDegreeId, academicDegreeDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{academicDegreeId}")
    public ResponseEntity modify(@PathVariable String personId,
                                 @PathVariable Integer academicDegreeId,
                                 @RequestBody Map<String, Object> fieldsToModify) {
        academicDegreeService.modify(personId, academicDegreeId, fieldsToModify);

        return new ResponseEntity(HttpStatus.OK);
    }
}
