package com.dins.Cars.rest;

import com.dins.Cars.model.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class CarRestController {

    public CarRestController(){
        getCars();
    }
    Map<Integer,CarPropertiesData> carPropList = CarsFullCharacteristics.getInstance().getAllCarsCharacteristics();

    @RequestMapping("/api/cars")
    public List<CarInfo> getCars(){
        List<CarInfo> carInfoList = new ArrayList<>();
        CarsCharacteristics.getInstance().getAllCarsCharacteristics().stream().forEach(x -> {
            CarInfo carInfo = new CarInfo(x.getId());
            carInfoList.add(carInfo);
        });
        return carInfoList;
    }

    @RequestMapping(value = "/api/cars/prop")
    @GetMapping
    public List<? extends CarInfo> controllerMethod(
            @RequestParam(name = "country",required = false) String country,
            @RequestParam(name = "segment",required = false) String segment,
            @RequestParam(name = "minEngineDisplacement",required = false) String minEngineDisplacement,
            @RequestParam(name = "minEngineHorsepower",required = false) String minEngineHorsepower,
            @RequestParam(name = "minMaxSpeed",required = false) String minMaxSpeed,
            @RequestParam(name = "bodyStyle",required = false) String bodyStyle,
            @RequestParam(name = "search",required = false) String search,
            @RequestParam(name = "isFull",required = false) Boolean isFull
    ) {
        if (minEngineDisplacement!=null & !NumberUtils.isParsable(minEngineDisplacement) ||
                minEngineHorsepower!=null & !NumberUtils.isParsable(minEngineHorsepower) ||
                minMaxSpeed!=null & !NumberUtils.isParsable(minMaxSpeed)
        ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Predicate<CarInfo> filter = c -> {
             {
                return
                (country == null || c.getCountry().equals(country)) &&
                (segment == null || c.getSegment().equals(segment)) &&
                (minEngineDisplacement == null || carPropList.get(c.getId()).getEngine_displacement() >= Integer.valueOf(minEngineDisplacement) &&
                (minEngineHorsepower == null || carPropList.get(c.getId()).getEngine_horsepower() >= Integer.valueOf(minEngineHorsepower)) &&
                (minMaxSpeed == null || carPropList.get(c.getId()).getMax_speed() >= Integer.valueOf(minMaxSpeed)) &&
                (bodyStyle == null || carPropList.get(c.getId()).getBody_style().equals(bodyStyle)) &&
                (search == null || carPropList.get(c.getId()).getModel().equals(search) || carPropList.get(c.getId()).getGeneration().equals(search) || carPropList.get(c.getId()).getModification().equals(search)));
            }
        };
        List<CarInfo> carInfoList = getCars().stream().filter(filter).collect(Collectors.toList());

        if (isFull != null && isFull==true){
            return getCarInfoFull().stream().filter(filter).collect(Collectors.toList());
        }

        return carInfoList;
    }

    private List<BrandData> getBrands(){
        return BrandsCharacteristics.getInstance().getAllBrandsCharacteristics();
    }

    @RequestMapping(
            value = "/api/engine-types",
            method = RequestMethod.GET)
    public List<String> allEngineTypes() {
        return carPropList.values().stream().map(c->c.getEngine_type()).distinct().collect(Collectors.toList());
    }

    @RequestMapping(
            value = "/api/wheel-drives",
            method = RequestMethod.GET)
    public List<String> allWheelDrives() {
        return carPropList.values().stream().map(c->c.getWheel_drive()).distinct().collect(Collectors.toList());
    }

    @RequestMapping(
            value = "/api/gearboxes",
            method = RequestMethod.GET)
    public List<String> allGearBoxes() {
        return carPropList.values().stream().map(c->c.getGearbox()).distinct().collect(Collectors.toList());
    }

    @RequestMapping(
            value = "/api/body-styles",
            method = RequestMethod.GET)
    public List<String> allBodyStyles() {
        return carPropList.values().stream().map(c->c.getBody_style()).distinct().collect(Collectors.toList());
    }

    @RequestMapping(
            value = "/api/max-speed",
            method = RequestMethod.GET)
    public Double getAvgSpeedbyBrand(
            @RequestParam(name = "brand",required = false) String brand,
            @RequestParam(name = "model",required = false) String model) {
        if ((model == null & brand == null) || (model != null & brand != null))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else if (model != null & brand == null){
            return carPropList.values().stream().filter(x->x.getModel().equals(model)).mapToInt(x->x.getMax_speed()).average().orElseThrow(()->{throw new ResponseStatusException(HttpStatus.NOT_FOUND);});
        }
        else
        {
            String brandId = getBrands().stream().filter(b->b.getTitle().equals(brand)).map(b->b.getId()).findFirst().orElse("-100");
            return carPropList.values().stream().filter(x->x.getBrand_id().equals(brandId)).mapToInt(x->x.getMax_speed()).average().orElseThrow(()->{throw new ResponseStatusException(HttpStatus.NOT_FOUND);});
        }
    }

    private List<CarInfoFull> getCarInfoFull() {
        List<CarInfoFull> carInfoFullList = new ArrayList<>();
        CarInfoFull carInfoFull;
        for (CarInfo c : getCars())
        {
            carInfoFull = new CarInfoFull(c.getId());
            carInfoFullList.add(carInfoFull);
        };
        return carInfoFullList;
    }
};











