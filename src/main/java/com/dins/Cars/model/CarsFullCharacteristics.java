package com.dins.Cars.model;

import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("singleton")
public class CarsFullCharacteristics {
    private static CarsFullCharacteristics instance;
    private Map<Integer,CarPropertiesData> carPropertiesDataList = new HashMap<>();

    private CarsFullCharacteristics() {
        getAllCarsPropertiesData();
    }
    public static CarsFullCharacteristics getInstance() {
        if(instance == null) {
            synchronized(CarsFullCharacteristics.class) {
                if(instance == null) {
                    instance = new CarsFullCharacteristics();
                }
            }
        }
        return instance;
    }

    private void getAllCarsPropertiesData(){
        RestTemplate restTemplate = new RestTemplate();
        CarsCharacteristics.getInstance().getAllCarsCharacteristics().stream().map(c -> c.getId()).forEach(id->
                {
                    carPropertiesDataList.put(id,
                        restTemplate.exchange(
                                "http://localhost:8084/api/v1/cars/" + id,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<CarPropertiesData>() {
                                }
                        ).getBody()
                    );
                }
        );
    }

    public Map<Integer,CarPropertiesData> getAllCarsCharacteristics(){
        return instance.carPropertiesDataList;
    }

    public CarPropertiesData getCarPropertiesById(int carId) {
        return getAllCarsCharacteristics().get(carId);
    }

}


