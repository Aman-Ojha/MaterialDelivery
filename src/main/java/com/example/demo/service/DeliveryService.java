package com.example.demo.service;

import com.example.demo.kafka.MaterialDeliveredEvent;
import com.example.demo.model.dto.MaterialDeliveryResponse;
import com.example.demo.repository.MaterialDeliveryRepository;
import com.example.demo.model.entity.MaterialDelivery;
import com.example.demo.model.dto.MaterialDeliveryRequest;
import com.example.demo.config.PlantConfig;
import jakarta.transaction.Transactional;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void recordDeliveryFromEvent(@Valid MaterialDeliveredEvent event) {

        var entity = new MaterialDelivery(
                null,
                event.materialId(),
                event.tons(),
                event.timestamp(),
                event.plantName()
        );

        materialDeliveryRepository.save(entity);
        // No return value – success = saved, failure = exception → goes to DLT
    }

    public long currentStock(String plantName){
        return materialDeliveryRepository.sumTonsByPlantName(plantName);
    }

    /** Get ALL deliveries (most recent first) */
    public List<MaterialDeliveryResponse> getAllDeliveries() {
        return materialDeliveryRepository.findAllByOrderByDeliveryTimeDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /** Get a single delivery by ID */
    public Optional<MaterialDeliveryResponse> getDeliveryById(Long id) {
        return materialDeliveryRepository.findById(id)
                .map(this::toResponse);
    }

    private MaterialDeliveryResponse toResponse(MaterialDelivery delivery) {
        return new MaterialDeliveryResponse(
                delivery.getId(),
                delivery.getMaterialId(),
                delivery.getTons(),
                delivery.getDeliveryTime(),
                delivery.getPlantName()
        );
    }

}
