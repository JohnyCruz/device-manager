package com.johny.challenge.manager.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.johny.challenge.manager.model.Device;
import com.johny.challenge.manager.repository.DeviceRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeviceRepository deviceRepository;

    @BeforeEach
    void BeforeEach() {
        deviceRepository.deleteAll();
    }

    @Test
    void testCreateDevice_with_success() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"name\", \"brand\":\"brand\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.brand").value("brand"))
                .andExpect(jsonPath("$.creationTime").exists())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Device device = objectMapper.readValue(json, Device.class);

        assertTrue(device.getId() > 0);

    }

    @Test
    void testDeleteDevice() throws Exception {

        // create the device
        MvcResult result = this.mockMvc.perform(post("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"name\", \"brand\":\"brand\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.brand").value("brand"))
                .andExpect(jsonPath("$.creationTime").exists())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Device device = objectMapper.readValue(json, Device.class);

        assertInstanceOf(Integer.class, device.getId());
        assertTrue(device.getId() > 0);

        // delete the device
        this.mockMvc.perform(delete("/devices/{id}", device.getId()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(""));
    }

    @Test
    void testGetAll_result_is_empty() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/devices/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        List<Device> devices = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertNotNull(devices);
        assertTrue(devices.isEmpty());
    }

    @Test
    void testGetAll_result_is_not_empty() throws Exception {
        this.mockMvc.perform(post("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"name\", \"brand\":\"brand\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.brand").value("brand"))
                .andExpect(jsonPath("$.creationTime").exists())
                .andExpect(jsonPath("$.id").exists());

        this.mockMvc.perform(post("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"name\", \"brand\":\"brand\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.brand").value("brand"))
                .andExpect(jsonPath("$.creationTime").exists())
                .andExpect(jsonPath("$.id").exists());

        MvcResult result = this.mockMvc.perform(get("/devices/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Device.class);
        List<Device> devices = objectMapper.readValue(json, collectionType);

        assertNotNull(devices);
        assertFalse(devices.isEmpty());
        assertEquals(2, devices.size());
    }

    @Test
    void testGetDeviceById_Success() throws Exception {

        // create the device
        MvcResult resultPost = this.mockMvc.perform(post("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"name\", \"brand\":\"brand\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.brand").value("brand"))
                .andExpect(jsonPath("$.creationTime").exists())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();

        String jsonPost = resultPost.getResponse().getContentAsString();
        Device devicePost = objectMapper.readValue(jsonPost, Device.class);

        assertInstanceOf(Integer.class, devicePost.getId());
        assertTrue(devicePost.getId() > 0);

        // get device by id
        MvcResult resultGet = this.mockMvc.perform(get("/devices/{id}", devicePost.getId()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonGet = resultGet.getResponse().getContentAsString();
        Device deviceGet = objectMapper.readValue(jsonGet, Device.class);

        assertEquals(devicePost.getId(), deviceGet.getId());
        assertEquals(devicePost.getBrand(), deviceGet.getBrand());
        assertEquals(devicePost.getName(), deviceGet.getName());
        assertEquals(devicePost.getCreationTime(), deviceGet.getCreationTime());
    }

    @Test
    void testfindDeviceById_Device_Not_Found() throws Exception {
        // get device by id
        this.mockMvc.perform(get("/devices/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("Device with id 1 Not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Not Found"));
    }

    @Test
    void testfindByBrand_result_is_not_empty() throws Exception {
        this.mockMvc.perform(post("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"name\", \"brand\":\"brand 1\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.brand").value("brand 1"))
                .andExpect(jsonPath("$.creationTime").exists())
                .andExpect(jsonPath("$.id").exists());

        this.mockMvc.perform(post("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"name\", \"brand\":\"brand 2\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.brand").value("brand 2"))
                .andExpect(jsonPath("$.creationTime").exists())
                .andExpect(jsonPath("$.id").exists());

        MvcResult result = this.mockMvc.perform(get("/devices/brand/{brand}", "brand 1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Device.class);
        List<Device> devices = objectMapper.readValue(json, collectionType);

        assertNotNull(devices);
        assertFalse(devices.isEmpty());
        assertEquals(1, devices.size());
        assertEquals("brand 1", devices.get(0).getBrand());
    }

    @Test
    void testfindByBrand_result_is_empty() throws Exception {

        this.mockMvc.perform(post("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"name\", \"brand\":\"brand 2\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.brand").value("brand 2"))
                .andExpect(jsonPath("$.creationTime").exists())
                .andExpect(jsonPath("$.id").exists());

        MvcResult result = this.mockMvc.perform(get("/devices/brand/{brand}", "brand 1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String json = result.getResponse().getContentAsString();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Device.class);
        List<Device> devices = objectMapper.readValue(json, collectionType);

        assertNotNull(devices);
        assertTrue(devices.isEmpty());
    }

    @Test
    void testUpdate_Update_partial() throws Exception {
        // create the device
        MvcResult resultPost = this.mockMvc.perform(post("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"name\", \"brand\":\"brand\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonPost = resultPost.getResponse().getContentAsString();
        Device devicePost = objectMapper.readValue(jsonPost, Device.class);

        assertInstanceOf(Integer.class, devicePost.getId());
        MvcResult resultPut = this.mockMvc.perform(put("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"new name\", \"brand\":\"new brand\", \"id\":\"" + devicePost.getId() + "\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonPut = resultPut.getResponse().getContentAsString();
        Device devicePut = objectMapper.readValue(jsonPut, Device.class);
        assertEquals(devicePost.getId(), devicePut.getId());
        assertNotEquals(devicePost.getName(), devicePut.getName());
        assertNotEquals(devicePost.getBrand(), devicePut.getBrand());
        assertEquals("new name",devicePut.getName());
        assertEquals( "new brand", devicePut.getBrand());
        assertEquals(devicePost.getCreationTime(), devicePut.getCreationTime());
    }

    @Test
    void testUpdate_Update_full() throws Exception {
        // create the device
        MvcResult resultPost = this.mockMvc.perform(post("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"name\", \"brand\":\"brand\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonPost = resultPost.getResponse().getContentAsString();
        Device devicePost = objectMapper.readValue(jsonPost, Device.class);

        assertInstanceOf(Integer.class, devicePost.getId());
        MvcResult resultPut = this.mockMvc.perform(put("/devices/?full=true")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"new name\", \"brand\":\"new brand\", \"id\":\"" + devicePost.getId() + "\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonPut = resultPut.getResponse().getContentAsString();
        Device devicePut = objectMapper.readValue(jsonPut, Device.class);
        assertEquals(devicePost.getId(), devicePut.getId());
        assertNotEquals(devicePost.getName(), devicePut.getName());
        assertNotEquals(devicePost.getBrand(), devicePut.getBrand());
        assertEquals("new name",devicePut.getName());
        assertEquals( "new brand", devicePut.getBrand());
        assertNotEquals(devicePost.getCreationTime(), devicePut.getCreationTime());
    }

    @Test
    void testUpdate_fail_to_Update_full() throws Exception {
        // create the device
        MvcResult resultPost = this.mockMvc.perform(post("/devices/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"name\", \"brand\":\"brand\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonPost = resultPost.getResponse().getContentAsString();
        Device devicePost = objectMapper.readValue(jsonPost, Device.class);

        assertInstanceOf(Integer.class, devicePost.getId());
        this.mockMvc.perform(put("/devices/?full=true")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"new name\", \"brand\":\"new brand\", \"id\":\"" + (devicePost.getId() + 1 )  + "\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value("Device with id " + (devicePost.getId() + 1 ) +" Not found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Not Found"));
    }
}