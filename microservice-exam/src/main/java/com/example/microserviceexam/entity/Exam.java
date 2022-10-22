package com.example.microserviceexam.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "subject")
    private String subject;
    @Column(name = "nbQuestion")
    private int nbQuestion;

}
