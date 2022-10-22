package com.example.microserviceexam.service;

import com.example.microserviceexam.entity.Exam;

import java.util.List;

public interface ExamService {
    Exam addExam (Exam e);
    Exam update(Exam exam, Long id);
    void delete(long id);
    List<Exam> findAll();
    Exam findbyid (Long id);
}
