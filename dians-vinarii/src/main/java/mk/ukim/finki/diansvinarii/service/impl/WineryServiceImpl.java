package mk.ukim.finki.diansvinarii.service.impl;

import mk.ukim.finki.diansvinarii.model.Winery;
import mk.ukim.finki.diansvinarii.repository.WineryRepository;
import mk.ukim.finki.diansvinarii.service.WineryService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class WineryServiceImpl implements WineryService {

    private final WineryRepository wineryRepository;

    public WineryServiceImpl(WineryRepository wineryRepository) {
        this.wineryRepository = wineryRepository;
    }

    @Override
    public List<Winery> searchWineries(String name) {
        return wineryRepository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Winery> findAll() {
        return wineryRepository.findAll();
    }

    @Override
    public Optional<Winery> findById(Long id) {
        return wineryRepository.findById(id);
    }

    @Override
    public void save(Long id, String name, String phone, String website, Double longitude, Double latitude, String openHours, String closeHours) throws Exception {
        Winery winery = wineryRepository.findById(id).orElseThrow(Exception::new);

        winery.setName(name);
        winery.setPhone(phone);
        winery.setWebsite(website);
        winery.setLongitude(longitude);
        winery.setLatitude(latitude);
        winery.setOpenHours(openHours);
        winery.setCloseHours(closeHours);

        wineryRepository.save(winery);
    }

    @Override
    public void deleteById(Long id) {
        wineryRepository.deleteById(id);
    }

    @Override
    public void edit(Long id, String name, String phone, String website, Double longitude, Double latitude, String openHours, String closeHours) throws Exception {
        Winery winery = wineryRepository.findById(id).orElseThrow(Exception::new);

        winery.setName(name);
        winery.setPhone(phone);
        winery.setWebsite(website);
        winery.setLongitude(longitude);
        winery.setLatitude(latitude);
        winery.setOpenHours(openHours);
        winery.setCloseHours(closeHours);

        wineryRepository.save(winery);
    }

}