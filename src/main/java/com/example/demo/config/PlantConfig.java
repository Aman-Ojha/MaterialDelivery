package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Positive;
// import lombok.Getter;
// import lombok.Setter;

@ConfigurationProperties(prefix = "plant")
@Validated
// @Getter
// @Setter
public class PlantConfig {
    private int maxTonnagePerHour = 5000;
    private String plantName = "Berlin Asphalt Plant";
    private boolean maintenanceMode = false;

    public int getMaxTonnagePerHour() {
        return maxTonnagePerHour;
    }

    public void setMaxTonnagePerHour(@Positive int maxTonnagePerHour) {
        this.maxTonnagePerHour = maxTonnagePerHour;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public boolean isMaintenanceMode() {
        return maintenanceMode;
    }

    public void setMaintenanceMode(boolean maintenanceMode) {
        this.maintenanceMode = maintenanceMode;
    }
}
