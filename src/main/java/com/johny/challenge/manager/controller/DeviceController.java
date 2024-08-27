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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService deviceService;

    @Operation(summary = "Get a list of all devices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Device.class)) }),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content) })
    @GetMapping(value = "/", produces = "application/json")

    public ResponseEntity<List<Device>> findAll() {
        logger.info("Calling find all devices");
        List<Device> devices = deviceService.findAll();
        return ResponseEntity.ok().body(devices);
    }

    @Operation(summary = "Get a device by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the device", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Device.class)) }),
            @ApiResponse(responseCode = "404", description = "Device not found", content = @Content) })
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Device> findById(@PathVariable("id") Integer id) {
        Device device = deviceService.findById(id);
        logger.info("device found: {}", device);
        return ResponseEntity.ok().body(device);
    }

    @Operation(summary = "Get a list of devices by brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the device", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Device.class)) }) })
    @GetMapping(value = "/brand/{brand}", produces = "application/json")
    public ResponseEntity<List<Device>> findByBrand(@PathVariable("brand") String brand) {
        logger.info("searching for devices with brand: {}", brand);
        List<Device> devices = deviceService.findByBrand(brand);
        logger.info("devices found: {}", devices);
        return ResponseEntity.ok().body(devices);
    }

    @Operation(summary = "Create a new device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Device.class)) }) })
    @PostMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Device> createDevice(@RequestBody Device device) throws URISyntaxException {
        logger.info("Create new device: {}", device);
        device = this.deviceService.create(device);
        logger.info("Device created successfully: {}", device);
        return ResponseEntity.created(new URI("/devices/" + device.getId())).body(device);
    }

    @Operation(summary = "Update a device")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Device Updated", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Device.class)) }) })
    @PutMapping(value = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Device> updateDevice(@RequestBody Device device,
            @RequestParam(required = false) boolean full) {
        logger.info("Will execute full update for Device {}? {}", device, full);
        Device updatedDevice = full ? deviceService.updateFull(device) : deviceService.update(device);
        return ResponseEntity.ok().body(updatedDevice);
    }

    @Operation(summary = "Delete a device by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Device deleted") })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable("id") Integer id) {
        deviceService.deleteDeviceById(id);
        return ResponseEntity.noContent().build();
    }

}
