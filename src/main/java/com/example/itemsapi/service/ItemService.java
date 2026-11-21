package com.example.itemsapi.service;

import com.example.itemsapi.model.Item;
import com.example.itemsapi.repository.ItemRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAll() {
        return itemRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Item> getById(Long id) {
        return itemRepository.findById(id);
    }

    public Item create(Item item) {
        item.setId(null);
        return itemRepository.save(item);
    }

    public Optional<Item> update(Long id, Item updated) {
        return itemRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setDescription(updated.getDescription());
            existing.setPrice(updated.getPrice());
            return itemRepository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (!itemRepository.existsById(id)) {
            return false;
        }
        itemRepository.deleteById(id);
        return true;
    }
}