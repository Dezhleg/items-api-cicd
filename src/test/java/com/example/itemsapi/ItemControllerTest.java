package com.example.itemsapi;

import com.example.itemsapi.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllItems() throws Exception {
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldGetItemById() throws Exception {
        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Keyboard"));
    }

    @Test
    void shouldReturn404WhenItemNotFound() throws Exception {
        mockMvc.perform(get("/api/items/404"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateItem() throws Exception {
        Item newItem = new Item(null, "Headset", "USB gaming headset", 60.0);

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Headset"));
    }

    @Test
    void shouldUpdateItem() throws Exception {
        Item updated = new Item(null, "Pro Mouse", "Updated model", 59.0);

        mockMvc.perform(put("/api/items/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pro Mouse"));
    }

    @Test
    void shouldDeleteItem() throws Exception {
        mockMvc.perform(delete("/api/items/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isNotFound());
    }
}
