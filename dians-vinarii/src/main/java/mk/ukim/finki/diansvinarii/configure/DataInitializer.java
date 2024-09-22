package mk.ukim.finki.diansvinarii.configure;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import mk.ukim.finki.diansvinarii.model.Winery;
import mk.ukim.finki.diansvinarii.repository.WineryRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Component
@AllArgsConstructor
public class DataInitializer {
    private final WineryRepository wineryRepository;



    @PostConstruct
    private void initializeData(){
        if (wineryRepository.findAll().isEmpty()){
            try (Scanner scanner = new Scanner(new File("src/main/resources/wineries.csv"))) {
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
                while (scanner.hasNextLine()) {
                    wineryRepository.save(Winery.csvItemToWinery(scanner.nextLine()));
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }
        }
    }
}
