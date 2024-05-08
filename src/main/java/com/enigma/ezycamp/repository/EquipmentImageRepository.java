package com.enigma.ezycamp.repository;

import com.enigma.ezycamp.entity.EquipmentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentImageRepository extends JpaRepository<EquipmentImage, String> {
}