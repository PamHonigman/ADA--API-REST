package com.backend11.myapiproject.dto;

public class AcademicDegreeDTO {

    private Integer id;
    private String title;
    private String institution;
    private String fecha;

    public AcademicDegreeDTO(String title, String institution, String fecha) {
        this.title = title;
        this.institution = institution;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getInstitution() {
        return institution;
    }

    public String getFecha() {
        return fecha;
    }
}
