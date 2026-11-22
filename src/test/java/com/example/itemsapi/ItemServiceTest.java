package com.example.itemsapi;

import com.example.itemsapi.model.Item;
import com.example.itemsapi.repository.ItemRepository;
import com.example.itemsapi.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void testFindAll() {
        Item item = new Item(1L, "Laptop", "Gaming laptop", 1500.00);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> result = itemService.findAll();
        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
    }

    @Test
    void testSaveAndFindById() {
        Item item = new Item(null, "Mouse", "Wireless mouse", 50.00);
        Item saved = new Item(1L, "Mouse", "Wireless mouse", 50.00);

        when(itemRepository.save(any(Item.class))).thenReturn(saved);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(saved));

        Item created = itemService.save(item);
        assertNotNull(created.getId());

        Optional<Item> found = itemService.findById(1L);
        assertTrue(found.isPresent());
        assertEquals("Mouse", found.get().getName());
    }
}
