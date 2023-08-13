package com.backend11.myapiproject.entity;

import com.backend11.myapiproject.exceptions.ResourceNotFoundException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "academic_degree")
public class AcademicDegree {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public AcademicDegree() {
    }

    public AcademicDegree(String title, String institution, LocalDate date, Person person) {
        this.title = title;
        this.institution = institution;
        this.date = date;
        this.person = person;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInstitution() {
        return institution;
    }

    public LocalDate getDate() {
        return date;
    }

    public Person getPerson() {
        return person;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void modifyAttributeValue(String attributeName, Object newValue) {
        switch (attributeName){
            case "title":
                this.title = (String) newValue;
                break;
            case "institution":
                this.institution = (String) newValue;
                break;
            case "fecha":
                this.date = LocalDate.parse((String)newValue, DATE_TIME_FORMATTER);
                break;
            default:
                throw new ResourceNotFoundException();
        }
    }
}

