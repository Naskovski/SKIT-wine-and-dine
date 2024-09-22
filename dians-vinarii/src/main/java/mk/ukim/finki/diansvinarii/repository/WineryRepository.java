package mk.ukim.finki.diansvinarii.repository;

import mk.ukim.finki.diansvinarii.model.Winery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WineryRepository extends JpaRepository<Winery, Long> {
    List<Winery> findAllByNameContainingIgnoreCase(String name);
    List<Winery> findAllByid(Long id);
    List<Winery> findAll();
    Optional<Winery> findById(Long id);

}
