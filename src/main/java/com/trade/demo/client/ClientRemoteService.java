package com.trade.demo.client;

import com.societegenerale.failover.annotations.Failover;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class ClientRemoteService {

    private final ClientRepository clientRepository;

    private final AtomicBoolean isAvailable = new AtomicBoolean(true);

    @Failover(name = "client-by-id", expiryDuration = 10, expiryUnit = ChronoUnit.MINUTES, domain = "client")
    public Client getClientById(Long id) {
        exceptionOnUnavailable();
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
    }

    @Failover(name = "client-by-str-ids", expiryDuration = 10, expiryUnit = ChronoUnit.MINUTES, payloadSplitter = "clientPayloadSplitter", domain = "client")
    public List<Client> getClientByStringIds(String ids) {
        exceptionOnUnavailable();
        return clientRepository.findAllByStringIds(ids);
    }

    @Failover(name = "client-by-ids", expiryDuration = 10, expiryUnit = ChronoUnit.MINUTES, payloadSplitter = "clientPayloadSplitter", domain = "client")
    public List<Client> getClientByIds(List<Long> ids) {
        exceptionOnUnavailable();
        return clientRepository.findAllByIds(ids);
    }



    @Failover(name = "client-all", expiryDuration = 10, expiryUnit = ChronoUnit.MINUTES, payloadSplitter = "clientAllPayloadSplitter", domain = "client")
    public List<Client> getAllClients() {
        exceptionOnUnavailable();
        return clientRepository.findAll();
    }

    private void exceptionOnUnavailable() {
        if (!isAvailable.get()) {
            var i = new Random().nextInt(6);
            switch (i) {
                case 1 -> throw new RuntimeException("Client service is unavailable", new SocketTimeoutException("Socket timed out"));
                case 2 -> throw new RuntimeException("Client service is unavailable", new TimeoutException("Request timed out"));
                case 3 -> throw new RuntimeException("Client service is unavailable", new ClientNotFoundException("Client not found"));
                case 4 -> throw new RuntimeException("Client service is unavailable", new IllegalStateException("Internal server error"));
                default -> throw new RuntimeException("Client service is unavailable", new IllegalAccessException("Access denied"));
            }
        }
    }

    public boolean toggleAvailable() {
        // toggle the value of AtomicBoolean isAvailable and return the new value
        return !isAvailable.getAndSet(!isAvailable.get());
    }
}
