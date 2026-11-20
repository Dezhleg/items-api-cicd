package com.example.itemsapi.service;

import com.example.itemsapi.model.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ItemService {

    private final Map<Long, Item> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ItemService() {
        create(new Item(null, "Keyboard", "Mechanical keyboard", 85.0));
        create(new Item(null, "Mouse", "Wireless gaming mouse", 45.5));
    }

    public List<Item> getAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Item> getById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Item create(Item item) {
        long id = idGenerator.getAndIncrement();
        item.setId(id);
        storage.put(id, item);
        return item;
    }

    public Optional<Item> update(Long id, Item updated) {
        if (!storage.containsKey(id)) {
            return Optional.empty();
        }
        updated.setId(id);
        storage.put(id, updated);
        return Optional.of(updated);
    }

    public boolean delete(Long id) {
        return storage.remove(id) != null;
    }
}
