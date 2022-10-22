package com.mcours.web.controller;

import com.mcours.dao.CourDao;
import com.mcours.model.Cour;
import com.mcours.web.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CourController {

    @Autowired
    CourDao courDao;

    // Affiche la liste de tous les cours disponibles
    @GetMapping(value = "/Cours")
    public List<Cour> listeDesCours(){

        List<Cour> cours = courDao.findAll();

        if(cours.isEmpty()) throw new ProductNotFoundException("Aucun cour n'est disponible à la vente");

        return cours;

    }

    //Récuperer un cour par son id
    @GetMapping( value = "/Cours/{id}")
    public Optional<Cour> recupererUnCour(@PathVariable int id) {

        Optional<Cour> cour = courDao.findById(id);

        if(!cour.isPresent())  throw new ProductNotFoundException("Le cour correspondant à l'id " + id + " n'existe pas");

        return cour;
    }
}

