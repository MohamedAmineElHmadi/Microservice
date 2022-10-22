package com.mcours.dao;

import com.mcours.model.Cour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourDao extends JpaRepository<Cour, Integer>{
}
