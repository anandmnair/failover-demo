package com.trade.demo.deal;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DealRepository {

    private static final Map<Long, Deal> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        for (long i = 1; i <= 10; i++) {
            store.put(i, Deal.builder().id(i).name("deal-" + i).amount(i*10L).clientId(i).build());
        }
    }

    public Optional<Deal> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Deal> findAll() {
        return store.values().stream().toList();
    }

    public List<Deal> findAllByIds(String ids) {
        return Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Long::valueOf)
                .map(store::get)
                .filter(Objects::nonNull)
                .toList();
    }
}