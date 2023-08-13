package com.backend11.myapiproject.entity;

import com.backend11.myapiproject.exceptions.ResourceNotFoundException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate birthday;

    private Character gender;

    @Column(name = "civil_status", nullable = false)
    private String civilStatus;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AcademicDegree> academicDegrees;

    public Person() {
    }

    public Person(String id, String name, String lastName, LocalDate birthday, Character gender, String civilStatus) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.civilStatus = civilStatus;
    }

    public Person(String id, String name, String lastName, LocalDate birthday, Character gender, String civilStatus, 
                  List<AcademicDegree> academicDegrees) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.civilStatus = civilStatus;
        this.academicDegrees = academicDegrees;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public Character getGender() {
        return gender;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public List<AcademicDegree> getAcademicDegrees() {
        if (academicDegrees == null) {
            academicDegrees = new ArrayList<>();
        }

        return academicDegrees;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public void setAcademicDegrees(List<AcademicDegree> academicDegrees) {
        this.academicDegrees = academicDegrees;
    }

    public void modifyAttributeValue(String attributeName, Object newValue) {
        switch (attributeName) {
            case "name":
                this.name = (String)newValue;
                break;
            case "last_name":
                this.lastName = (String)newValue;
                break;
            case "birthday":
                this.birthday = LocalDate.parse((String)newValue, DATE_TIME_FORMATTER);
                break;
            case "gender":
                this.gender = (Character)newValue;
                break;
            case "civil_status":
                this.civilStatus = (String)newValue;
                break;
            default:
                throw new ResourceNotFoundException();
        }
    }
}
