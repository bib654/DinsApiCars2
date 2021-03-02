package com.dins.Cars.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarData {
    @JsonProperty("id")
    private int id;
    @JsonProperty("segment")
    private String segment;
    @JsonProperty("brand_id")
    private String brand_id;
    @JsonProperty("model")
    private String model;
    @JsonProperty("generation")
    private String generation;
    @JsonProperty("modification")
    private String modification;
}
