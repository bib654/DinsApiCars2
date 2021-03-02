package com.dins.Cars.model;

import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("singleton")
public class CarsCharacteristics {
    private static CarsCharacteristics instance;
    private List<CarData> carDataList;

    private CarsCharacteristics() {
        getAllCarsData();
    }

    public static CarsCharacteristics getInstance() {
        if(instance == null) {
            synchronized(CarsCharacteristics.class) {
                if(instance == null) {
                    instance = new CarsCharacteristics();
                }
            }
        }
        return instance;
    }

    private void getAllCarsData(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<CarData>> responseEntity =
                restTemplate.exchange(
                        "http://localhost:8084/api/v1/cars",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<CarData>>() {}
                );
        carDataList = responseEntity.getBody();
    }

    public List<CarData> getAllCarsCharacteristics(){
        return instance.carDataList;
    }

    public CarData getCarDataById(int carId){
        return getAllCarsCharacteristics().stream().filter(car -> car.getId() == carId).findFirst().orElse(new CarData());
    }

}


