package com.example.demo.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "material_delivery")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString // optional but nice
public class MaterialDelivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "material_id", nullable = false)
    private String materialId;

    @Column(nullable = false)
    private int tons;

    @Column(name = "delivery_time", nullable = false)
    private LocalDateTime deliveryTime;

    @Column(name = "plant_name", nullable = false)
    private String plantName;
}