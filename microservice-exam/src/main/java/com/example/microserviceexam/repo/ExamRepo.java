package com.example.microserviceexam.repo;

import com.example.microserviceexam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepo extends JpaRepository<Exam,Long> {
}
