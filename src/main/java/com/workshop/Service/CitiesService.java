package com.workshop.Service;

import com.workshop.Entity.Cities;
import com.workshop.Repo.CitiesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CitiesService {

    @Autowired
    private CitiesRepository citiesRepository;

    public List<Cities> getAllCities() {
        return citiesRepository.findAll();
    }

    public Optional<Cities> getCityById(Long id) {
        return citiesRepository.findById(id);
    }

    public List<Cities> getCitiesByState(Long stateId) {
        return citiesRepository.findByStateId(stateId);
    }

    
    
}








    
    

