package com.dins.Cars.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarPropertiesData {
    private int id;
    private String segment;
    private String brand_id;
    private String model;
    private String generation;
    private String modification;
    private String year_range;
    private String engine_type;
    private String engine_cylinders;
    private int engine_displacement;
    private int engine_horsepower;
    private String gearbox;
    private String wheel_drive;
    private String body_length;
    private String body_width;
    private String body_height;
    private String body_style;
    private String acceleration;
    private int max_speed;
}

