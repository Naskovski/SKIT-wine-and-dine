package mk.ukim.finki.diansvinarii.service;

import mk.ukim.finki.diansvinarii.model.Winery;

import java.util.List;
import java.util.Optional;

public interface WineryService {
    List<Winery> searchWineries(String text);
    List<Winery> findAll();
    Optional<Winery> findById(Long id) throws IllegalArgumentException;
    void save(Long id, String name, String phone, String website, Double longitude, Double latitude, String openHours, String closeHours) throws Exception;
    void edit(Long id, String name, String phone, String website, Double longitude, Double latitude, String openHours, String closeHours) throws Exception;
    void deleteById(Long id);
}
