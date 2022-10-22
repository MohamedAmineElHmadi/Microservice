package com.example.microserviceexam.controller;

import com.example.microserviceexam.entity.Exam;
import com.example.microserviceexam.service.ExamService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Exams/")
@AllArgsConstructor
public class ExamController {
    private final ExamService examService;
    @GetMapping("list")
    public List<Exam> findAll() {
        return examService.findAll();
    }
    @PostMapping( "save")
    public Exam saveOption(@RequestBody Exam e ){
        return    examService.addExam(e);
    }
    @GetMapping("findById/{id}")
    public Exam findById(@PathVariable("id") Long id) {
        return examService.findbyid(id);
    }
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") long id) throws Exception {
        examService.delete(id);
    }
    @PutMapping("update/{id}")
    public Exam update(@RequestBody Exam e, @PathVariable("id") Long id) {
        Exam updateExam = examService.update(e, id);
        return updateExam;
    }
}
