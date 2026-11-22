package com.example.itemsapi;

import com.example.itemsapi.controller.ItemController;
import com.example.itemsapi.model.Item;
import com.example.itemsapi.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ItemControllerTest {

    private MockMvc mockMvc;

    static class StubItemService extends ItemService {
        private final List<Item> store = new ArrayList<>();

        StubItemService() {
            super(null);
            store.add(new Item(1L, "Book", "Java Guide", 25.0));
        }

        @Override
        public List<Item> findAll() {
            return store;
        }

        @Override
        public Optional<Item> findById(Long id) {
            return store.stream().filter(i -> i.getId().equals(id)).findFirst();
        }

        @Override
        public Item save(Item item) {
            item.setId(2L);
            store.add(item);
            return item;
        }
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ItemController(new StubItemService())).build();
    }

    @Test
    void shouldReturnItemsList() throws Exception {
        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Book"));
    }

    @Test
    void shouldReturnItemByIdOr404() throws Exception {
        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Book"));

        mockMvc.perform(get("/api/items/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateItem() throws Exception {
        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Pen\",\"description\":\"Blue pen\",\"price\":2.5}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2));
    }
}
