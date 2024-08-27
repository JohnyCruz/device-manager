package com.johny.challenge.manager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.johny.challenge.manager.model.Device;
import com.johny.challenge.manager.service.DeviceService;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    
    Logger logger = LoggerFactory.getLogger(DeviceController.class);
    
    @Autowired
    private DeviceService deviceService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Device>> findAll() {
        logger.info("Calling find all devices");
        List<Device> devices = deviceService.findAll();
        return ResponseEntity.ok().body(devices);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Device> findById(@PathVariable("id") Integer id) {
        Device device = deviceService.findById(id);
        logger.info("device found: {}", device);
        return ResponseEntity.ok().body(device);
    }

    @GetMapping(value = "/brand/{brand}", produces = "application/json")
    public ResponseEntity<List<Device>> findByBrand(@PathVariable("brand") String brand) {
        logger.info("searching for devices with brand: {}", brand);
        List<Device> devices = deviceService.findByBrand(brand);
        logger.info("devices found: {}", devices);
        return ResponseEntity.ok().body(devices);
    }

    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Device> createDevice(@RequestBody Device device) {
        logger.info("Create new device: {}", device);
        device = this.deviceService.create(device);
        logger.info("Device created successfully: {}", device);
        return ResponseEntity.ok().body(device);
    }

    @PutMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Device> updateDevice(@RequestBody Device device,
            @RequestParam(required = false) boolean full) {
        logger.info("Will execute full update for Device {}? {}", device, full);
        Device updatedDevice = full ? deviceService.updateFull(device) : deviceService.update(device);
        return ResponseEntity.ok().body(updatedDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable("id") Integer id) {
        deviceService.deleteDeviceById(id);
        return ResponseEntity.noContent().build();
    }

}
