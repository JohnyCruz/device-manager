package com.johny.challenge.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johny.challenge.manager.model.Device;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

    List<Device> findByBrand(String brand);
}
