package com.example.application.data.service;

import java.util.Optional;
import java.util.UUID;

import com.example.application.data.entity.MeritItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MeritItemService {

    private final MeritItemRepository repository;

    @Autowired
    public MeritItemService(MeritItemRepository repository) {
        this.repository = repository;
    }

    public Optional<MeritItem> get(long id) {
        return repository.findById(id);
    }

    public MeritItem update(MeritItem entity) {
        return repository.save(entity);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public Page<MeritItem> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }
}
