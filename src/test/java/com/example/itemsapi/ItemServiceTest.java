package com.example.itemsapi;

import com.example.itemsapi.model.Item;
import com.example.itemsapi.repository.ItemRepository;
import com.example.itemsapi.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService(itemRepository);
    }

    @Test
    void shouldReturnInitialItems() {
        when(itemRepository.findAll(any(Sort.class))).thenReturn(List.of(
                new Item(1L, "Keyboard", "Mechanical keyboard", 85.0),
                new Item(2L, "Mouse", "Wireless gaming mouse", 45.5)
        ));
        List<Item> items = itemService.getAll();
        assertEquals(2, items.size());
    }

    @Test
    void shouldCreateAndFindItem() {
        Item toSave = new Item(null, "Monitor", "27 inch 144Hz", 299.99);
        Item saved = new Item(3L, "Monitor", "27 inch 144Hz", 299.99);
        when(itemRepository.save(any(Item.class))).thenReturn(saved);
        when(itemRepository.findById(3L)).thenReturn(Optional.of(saved));

        Item created = itemService.create(toSave);
        assertEquals(3L, created.getId());

        Optional<Item> found = itemService.getById(3L);
        assertTrue(found.isPresent());
        assertEquals("Monitor", found.get().getName());
    }

    @Test
    void shouldUpdateExistingItem() {
        Item existing = new Item(1L, "Keyboard", "Mechanical keyboard", 85.0);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(itemRepository.save(any(Item.class))).thenAnswer(inv -> inv.getArgument(0));

        Item updated = new Item(null, "Updated Keyboard", "New switches", 95.0);
        Optional<Item> result = itemService.update(1L, updated);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Updated Keyboard", result.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenUpdatingMissingItem() {
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<Item> result = itemService.update(999L, new Item(null, "Ghost", "None", 0));
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldDeleteItem() {
        when(itemRepository.existsById(1L)).thenReturn(true).thenReturn(false);
        assertTrue(itemService.delete(1L));
        assertFalse(itemService.delete(1L));
        verify(itemRepository, times(1)).deleteById(1L);
    }
}