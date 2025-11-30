package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.PlantConfig;
import com.example.demo.model.dto.MaterialDeliveryRequest;
import com.example.demo.model.entity.MaterialDelivery;
import com.example.demo.service.DeliveryService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/deliveries")
@Validated
@Slf4j
public class DeliveryController {

    //members
    private final PlantConfig plantConfig;
    private final DeliveryService deliveryService;

    //constructor
    public DeliveryController(PlantConfig plantConfig, DeliveryService deliveryService){
        this.deliveryService = deliveryService;
        this.plantConfig = plantConfig;
    }

    @PostMapping("/material")
    public ResponseEntity<String> receiveMaterialDelivery(@Valid @RequestBody MaterialDeliveryRequest request){
        if (plantConfig.isMaintenanceMode()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                 .body("Plant is under maintenance. Cannot accept deliveries at this time.");
        }
        if(request.tons() > plantConfig.getMaxTonnagePerHour()) {
            throw new IllegalArgumentException("Exceeds hourly limit");
        }

        // Record the delivery
        Long id = deliveryService.recordDelivery(request);
        log.info("Delivery recorded with id = {}", id);

        //TODO: Send to kafka 

        return ResponseEntity.accepted().build();
        

    }

}
