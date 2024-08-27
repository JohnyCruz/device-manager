package com.johny.challenge.manager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DeviceRequestBody {

    @NotBlank(message = "The Name is required.")
    private String name;

    @NotBlank(message = "The Brand is required.")
    private String brand;

    public Device toDevice(){
        Device device = new Device();
        device.setBrand(brand);
        device.setName(name);
        return device;
    }

}
