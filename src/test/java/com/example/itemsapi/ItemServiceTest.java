package com.example.itemsapi;

import com.example.itemsapi.model.Item;
import com.example.itemsapi.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService();
    }

    @Test
    void shouldReturnInitialItems() {
        List<Item> items = itemService.getAll();
        assertEquals(2, items.size());
    }

    @Test
    void shouldCreateAndFindItem() {
        Item created = itemService.create(new Item(null, "Monitor", "27 inch 144Hz", 299.99));
        assertNotNull(created.getId());

        Optional<Item> found = itemService.getById(created.getId());
        assertTrue(found.isPresent());
        assertEquals("Monitor", found.get().getName());
    }

    @Test
    void shouldUpdateExistingItem() {
        Item updated = new Item(null, "Updated Keyboard", "New switches", 95.0);
        Optional<Item> result = itemService.update(1L, updated);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Updated Keyboard", result.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenUpdatingMissingItem() {
        Optional<Item> result = itemService.update(999L, new Item(null, "Ghost", "None", 0));
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldDeleteItem() {
        assertTrue(itemService.delete(1L));
        assertTrue(itemService.getById(1L).isEmpty());
        assertFalse(itemService.delete(1L));
    }
}
