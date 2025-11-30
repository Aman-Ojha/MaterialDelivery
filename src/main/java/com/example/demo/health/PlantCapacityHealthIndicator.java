// // src/main/java/com/example/demo/health/PlantCapacityHealthIndicator.java
// package com.example.demo.health;

// import org.springframework.boot.actuate.health.Health;
// import org.springframework.boot.actuate.health.HealthIndicator;
// import org.springframework.stereotype.Component;

// @Component
// public class PlantCapacityHealthIndicator implements HealthIndicator {

//     @Override
//     public Health health() {
//         // Replace with real logic later (e.g. last hour tonnage from DB)
//         return Health.up()
//                 .withDetail("plant", "Berlin Asphalt Plant")
//                 .withDetail("maxTonnagePerHour", 4800)
//                 .withDetail("currentLoad", "68%")
//                 .withDetail("status", "Operating normally")
//                 .build();
//     }
// }