package com.dins.Cars.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarInfo {
    private int id;
    private String segment;
    private String brand;
    private String model;
    private String country;
    private String generation;
    private String modification;

    public CarInfo(int carId){
        CarData carData = CarsCharacteristics.getInstance().getCarDataById(carId);
        BrandData brandData = BrandsCharacteristics.getInstance().getBrandDataById(carData.getBrand_id());
        this.setId(carData.getId());
        this.setSegment(carData.getSegment());
        this.setBrand(brandData.getTitle());
        this.setModel(carData.getModel());
        this.setCountry(brandData.getCountry());
        this.setGeneration(carData.getGeneration());
        this.setModification(carData.getModification());
    }



}
