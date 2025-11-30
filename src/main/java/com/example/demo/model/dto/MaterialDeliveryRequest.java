package com.example.demo.model.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MaterialDeliveryRequest(@NotBlank String materialId, @Positive int tons,
                                     @NotNull LocalDateTime deliveryTime) {
    

}
