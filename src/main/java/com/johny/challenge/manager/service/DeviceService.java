package com.johny.challenge.manager.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johny.challenge.manager.model.Device;
import com.johny.challenge.manager.repository.DeviceRepository;

@Service
public class DeviceService {
    Logger logger = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    private DeviceRepository deviceRepository;

    public Device create(Device device) {
        device.setCreationTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        return deviceRepository.save(device);
    }

    public List<Device> findAll() {
        List<Device> devices = deviceRepository.findAll();
        logger.info("the following devices was found in the database: {}", devices);
        return devices;
    }

    public Device findById(Integer id) {
        logger.info("searching for device with id: {}", id);
        return deviceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Device with id " + id + " Not found"));
    }

    public List<Device> findByBrand(String brand) {
        return deviceRepository.findByBrand(brand);
    }

    public void deleteDeviceById(Integer id) {
        deviceRepository.deleteById(id);
    }

    public Device updateFull(Device device) {
        if (deviceRepository.findById(device.getId()).isPresent()) {
            device.setCreationTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
            return deviceRepository.save(device);
        }
        throw new NoSuchElementException("Device with id " + device.getId() + " Not found");
    }

    public Device update(Device device) {
        Device oldDevice = deviceRepository.findById(device.getId())
                .orElseThrow(() -> new NoSuchElementException("Device with id " + device.getId() + " Not found"));
        oldDevice.setName(device.getName());
        oldDevice.setBrand(device.getBrand());
        return deviceRepository.save(oldDevice);
    }
}
