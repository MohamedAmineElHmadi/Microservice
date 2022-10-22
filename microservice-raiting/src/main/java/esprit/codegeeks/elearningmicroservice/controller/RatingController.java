package esprit.codegeeks.elearningmicroservice.controller;

import esprit.codegeeks.elearningmicroservice.domain.Rating;
import esprit.codegeeks.elearningmicroservice.service.IRatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
@Slf4j
public class RatingController {

    private final IRatingService ratingService;

    @GetMapping("/course/{courseid}")
    public Integer getRatingForCourse(@PathVariable("courseid") Long courseid){
      return ratingService.getCoursesRating(courseid);

    }

    @GetMapping("/{id}")
    public Rating getRating(@PathVariable("id") Long id) throws Exception {

        return ratingService.getRatingById(id).orElseThrow(()->new Exception("Personne n'existe pas"));

    }

    @GetMapping
    public List<Rating> getRatings(){
        return ratingService.getratings();
    }


    @PostMapping()
    public Rating addRating(@RequestBody Rating rating){
      return   ratingService.addRating(rating);
    }
    @DeleteMapping("/{id}")
    public void deleteRating(@PathVariable("id") Long id){
        ratingService.deleteRating(id);
    }
}


