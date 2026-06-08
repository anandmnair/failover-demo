package com.trade.demo.deal;

import com.trade.demo.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;
    private final ClientService clientService;

    public Deal findById(Long id) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deal not found: " + id));
        return enrich(deal);
    }

    public List<Deal> findAllByIds(String ids) {
        return dealRepository.findAllByIds(ids).stream()
                .map(this::enrich)
                .toList();
    }

    public List<Deal> findAll() {
        return dealRepository.findAll().stream()
                .map(this::enrich)
                .toList();
    }

    private Deal enrich(Deal deal) {
        deal.setClient(clientService.getClientById(deal.getClientId()));
        return deal;
    }
}