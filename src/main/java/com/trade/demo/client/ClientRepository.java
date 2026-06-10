package com.trade.demo.client;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ClientRepository {

    private static final Map<Long, Client> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        for (long i = 1; i <= 10; i++) {
            store.put(i, Client.builder().id(i).name("client-" + i).score(i).build());
        }
    }

    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Client> findAll() {
        return store.values().stream().toList();
    }

    public List<Client> findAllByStringIds(String ids) {
        return Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Long::valueOf)
                .map(store::get)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<Client> findAllByIds(List<Long> ids) {
        return ids.stream()
                .map(store::get)
                .filter(Objects::nonNull)
                .toList();
    }
}
