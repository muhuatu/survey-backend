package com.yuina.survey.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "quiz")
public class Quiz {

    @GeneratedValue(strategy = GenerationType.IDENTITY) // 屬性為Integer必加，int的話都可
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "quiz_name")
    private String name;

    @Column(name = "description")
    private String description;

    @JsonProperty("start_date")
    @Column(name = "start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "published")
    private boolean published;

    public Quiz() {
    }

    public Quiz(String name, String description, LocalDate startDate, LocalDate endDate,
                boolean published) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.published = published;
    }

    public Quiz(int id, String name, String description, LocalDate startDate, LocalDate endDate,
                boolean published) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.published = published;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

}
