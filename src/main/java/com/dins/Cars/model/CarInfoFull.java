package com.dins.Cars.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarInfoFull extends CarInfo {
    private String engine_type;
    private String engine_cylinders;
    private int engine_displacement;
    private int engine_horsepower;
    private String body_length;
    private String body_width;
    private String body_height;
    private String body_style;

    public CarInfoFull(int carId){
        super(carId);
        CarPropertiesData carPropertiesData = CarsFullCharacteristics.getInstance().getCarPropertiesById(carId);
        CarData carData = CarsCharacteristics.getInstance().getCarDataById(carId);
        BrandData brandData = BrandsCharacteristics.getInstance().getBrandDataById(carData.getBrand_id());
        this.setId(carPropertiesData.getId());
        this.setSegment(carPropertiesData.getSegment());
        this.setBrand(brandData.getTitle());
        this.setModel(carData.getModel());
        this.setCountry(brandData.getCountry());
        this.setGeneration(carData.getGeneration());
        this.setModification(carData.getModification());
        this.setEngine_type(carPropertiesData.getEngine_type());
        this.setEngine_cylinders(carPropertiesData.getEngine_cylinders());
        this.setEngine_displacement(carPropertiesData.getEngine_displacement());
        this.setEngine_horsepower(carPropertiesData.getEngine_horsepower());
        this.setBody_length(carPropertiesData.getBody_length());
        this.setBody_width(carPropertiesData.getBody_width());
        this.setBody_height(carPropertiesData.getBody_height());
        this.setBody_style(carPropertiesData.getBody_style());
    }

}