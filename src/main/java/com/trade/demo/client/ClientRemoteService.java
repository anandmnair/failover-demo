package com.trade.demo.client;

import com.societegenerale.failover.annotations.Failover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class ClientRemoteService {

    private final ClientRepository clientRepository;

    private final AtomicBoolean isAvailable = new AtomicBoolean(true);

    @Failover(name = "client-by-id", expiryDuration = 10, expiryUnit = ChronoUnit.MINUTES)
    public Client getClientById(Long id) {
        if (!isAvailable.get()) {
            throw new IllegalStateException("Client not available");
        }
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
    }

    @Failover(name = "client-by-ids", expiryDuration = 10, expiryUnit = ChronoUnit.MINUTES)
    public List<Client> getClientByIds(String ids) {
        if (!isAvailable.get()) {
            throw new IllegalStateException("Client not available");
        }
        return clientRepository.findAllByIds(ids);
    }

    @Failover(name = "client-all", expiryDuration = 10, expiryUnit = ChronoUnit.MINUTES)
    public List<Client> getClientByIds() {
        if (!isAvailable.get()) {
            throw new IllegalStateException("Client not available");
        }
        return clientRepository.findAll();
    }

    public boolean toggleAvailable() {
        // toggle the value of AtomicBoolean isAvailable and return the new value
        return !isAvailable.getAndSet(!isAvailable.get());
    }
}
