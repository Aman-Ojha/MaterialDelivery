package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.entity.MaterialDelivery;

import java.time.LocalDateTime;
import java.util.List;

public interface MaterialDeliveryRepository extends JpaRepository<MaterialDelivery, Long> {
    List<MaterialDelivery> findByPlantNameOrderByDeliveryTimeDesc(String plantName);

    List<MaterialDelivery> findAllByOrderByDeliveryTimeDesc();

    List<MaterialDelivery> findByMaterialIdOrderByDeliveryTimeDesc(String materialId);

    List<MaterialDelivery> findByDeliveryTimeBetweenOrderByDeliveryTimeDesc(
            LocalDateTime start, LocalDateTime end);

    @Query("SELECT COALESCE(SUM(d.tons),0) FROM MaterialDelivery d where d.plantName = :plantName")
    long sumTonsByPlantName(@Param("plantName") String plantName);
}
