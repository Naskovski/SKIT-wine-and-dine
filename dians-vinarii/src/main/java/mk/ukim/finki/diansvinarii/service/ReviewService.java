package mk.ukim.finki.diansvinarii.service;

import mk.ukim.finki.diansvinarii.model.Review;

import java.util.List;

public interface ReviewService {

    public List<Review> findAllByWinery_Id(Long id);
    public Double getWineryAverageScoreById(Long id);
    public List<Review> getNMostRecentByWineryId(Long id, int n);
    public List<Review> getNBestByWineryId(Long id, int n);
    public List<Review> findAllByWinery_IdOrderByScore(Long id);
    public Review add(Review review);
    public Review findById(Long id);
    public void delete(Review review);


}
