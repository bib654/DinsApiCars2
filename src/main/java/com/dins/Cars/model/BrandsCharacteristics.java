package com.dins.Cars.model;

import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Scope("singleton")
public class BrandsCharacteristics {
    private static BrandsCharacteristics instance;
    private List<BrandData> brandDataList;

    private BrandsCharacteristics() {
        getAllBrandsData();
    }
    public static BrandsCharacteristics getInstance() {
        if(instance == null) {
            synchronized(BrandsCharacteristics.class) {
                if(instance == null) {
                    instance = new BrandsCharacteristics();
                }
            }
        }
        return instance;
    }

    private void getAllBrandsData(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<BrandData>> responseEntity =
                restTemplate.exchange(
                        "http://localhost:8084/api/v1/brands",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<BrandData>>() {}
                );
        brandDataList = responseEntity.getBody();
    }

    public List<BrandData> getAllBrandsCharacteristics(){
        return instance.brandDataList;
    }

    public BrandData getBrandDataById(String brandId){
        return getAllBrandsCharacteristics().stream().filter(brand -> brand.getId().equals(brandId)).findFirst().orElse(new BrandData());
    }

}


