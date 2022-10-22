package esprit.codegeeks.elearningmicroservice.repository;

import esprit.codegeeks.elearningmicroservice.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {
//    @Override
//    Optional<Rating> findById(Long aLong);

     List<Rating> findRatingsByCourse_Id(Long id);

//    @Override
//    List<Rating> findAll();

//    @Override
//    void deleteById(Long aLong);


}

