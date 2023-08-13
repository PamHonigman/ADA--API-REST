package com.backend11.myapiproject.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class PersonDTO {

    private String id;
    private String name;
    @JsonAlias("last_name")
    private String lastName;
    private String birthday;
    private char gender;
    @JsonAlias("civil_status")
    private String civilStatus;

    @JsonAlias("academic_degrees")
    private List<AcademicDegreeDTO> academicDegreeDTOS;


    public PersonDTO(){}

    public PersonDTO(String id, String name, String lastName, String birthday, char gender, String civilStatus,
                     List<AcademicDegreeDTO> academicDegreeDTOS) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.civilStatus = civilStatus;
        this.academicDegreeDTOS = academicDegreeDTOS;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public char getGender() {
        return gender;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public List<AcademicDegreeDTO> getAcademicDegreeDTOS() {
        return academicDegreeDTOS;
    }
}
