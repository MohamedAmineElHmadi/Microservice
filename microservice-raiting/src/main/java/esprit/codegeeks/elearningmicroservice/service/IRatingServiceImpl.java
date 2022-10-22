package esprit.codegeeks.elearningmicroservice.service;

import esprit.codegeeks.elearningmicroservice.domain.Rating;
import esprit.codegeeks.elearningmicroservice.repository.RatingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IRatingServiceImpl implements IRatingService {
    private final RatingRepo ratingRepo;

    @Override
    public Rating addRating(Rating rating) {
        return ratingRepo.save(rating);
    }

    @Override
    public Integer getCoursesRating(Long id) {

        return ratingRepo.findRatingsByCourse_Id(id).stream().mapToInt(Rating::getRating).sum() / ratingRepo.findRatingsByCourse_Id(id).size();

    }

    @Override
    public void deleteRating(Long id) {
        ratingRepo.deleteById(id);
    }

    @Override
    public List<Rating> getratings() {
        return ratingRepo.findAll();
    }

    @Override
    public Optional<Rating> getRatingById(Long id) {
        return ratingRepo.findById(id);
    }


}
