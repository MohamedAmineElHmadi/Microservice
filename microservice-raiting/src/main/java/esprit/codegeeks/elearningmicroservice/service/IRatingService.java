package esprit.codegeeks.elearningmicroservice.service;

import esprit.codegeeks.elearningmicroservice.domain.Rating;

import java.util.List;
import java.util.Optional;

public interface IRatingService {
    Rating addRating(Rating rating);
    Integer getCoursesRating(Long id);
void deleteRating(Long id);
List<Rating> getratings();

Optional<Rating> getRatingById(Long id);


}
