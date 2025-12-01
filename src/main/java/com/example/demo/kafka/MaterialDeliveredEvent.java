package com.example.demo.kafka;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record MaterialDeliveredEvent(
        @NotBlank        // or @NotNull if String can be empty
        String materialId,

        @Max(value = 9999)
        int tons,

        @NotNull
        @PastOrPresent
        LocalDateTime timestamp,

        @NotBlank
        String plantName

) {

}
