package mk.ukim.finki.diansvinarii.controller;

import lombok.AllArgsConstructor;
import mk.ukim.finki.diansvinarii.model.Winery;
import mk.ukim.finki.diansvinarii.service.WineryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Validated
@CrossOrigin(origins="*")
@AllArgsConstructor
public class WineryController {
    private final WineryService wineryService;

    @GetMapping("/search")
    public ResponseEntity<List<Winery>> getWineriesBySearch(@RequestParam (required = false) String query){
        if(query!=null && !query.trim().isEmpty())
            return new ResponseEntity<>(wineryService.searchWineries(query), HttpStatus.OK);

        return new ResponseEntity<>(wineryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Winery>> getAllWineries(){
        return new ResponseEntity<>(wineryService.findAll(), HttpStatus.OK);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
