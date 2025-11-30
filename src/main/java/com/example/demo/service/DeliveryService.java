package com.example.demo.service;

import com.example.demo.repository.MaterialDeliveryRepository;
import com.example.demo.model.entity.MaterialDelivery;
import com.example.demo.model.dto.MaterialDeliveryRequest;
import com.example.demo.config.PlantConfig;
import jakarta.transaction.Transactional;


import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final MaterialDeliveryRepository materialDeliveryRepository;
    private final PlantConfig plantConfig;

    @Transactional
    public long recordDelivery(MaterialDeliveryRequest request){
        if(request.tons()>plantConfig.getMaxTonnagePerHour()) {
            throw new IllegalArgumentException("Tonnage exceeds maximum allowed per hour");
        }

        var entity = new MaterialDelivery( 
            null,
            request.materialId(),
            request.tons(),
            request.deliveryTime(),
            plantConfig.getPlantName()
        );

        return materialDeliveryRepository.save(entity).getId();


    }

    public long currentStock(String plantName){
        return materialDeliveryRepository.sumtTonsByPlantName(plantName);
    }

}
