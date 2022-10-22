package com.example.microserviceexam.service;

import com.example.microserviceexam.entity.Exam;
import com.example.microserviceexam.repo.ExamRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class ExamServiceImp implements ExamService {
    private final ExamRepo examRepo;
    @Override
    public Exam addExam(Exam e) {
        return examRepo.save(e);
    }

    @Override
    public Exam update(Exam exam, Long id) {
        if( examRepo.findById(id ).isPresent()){
            return  examRepo.save(exam);
        }
        else {
            return null;
        }    }

    @Override
    public void delete(long id) {
        examRepo.deleteById(id);
    }

    @Override
    public List<Exam> findAll() {
        return examRepo.findAll();
    }

    @Override
    public Exam findbyid(Long id) {
        return examRepo.findById(id).get(
        );
    }
}
