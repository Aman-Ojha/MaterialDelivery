package com.example.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDeliveryResponse {

    // public MaterialDeliveryResponse(
    // Long id, String materialId, Double tons, LocalDateTime deliveryTime, String plantName
    // ) {
    //     this.id = id;
    //     this.materialId = materialId;
    //     this.tons = tons;
    //     this.deliveryTime = deliveryTime;
    //     this.plantName = plantName;

    // }

    private Long id;
    private String materialId;
    private int tons;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime deliveryTime;

    private String plantName;
}