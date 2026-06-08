package com.trade.demo.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRemoteService clientRemoteService;

    public Client getClientById(Long id) {
        return clientRemoteService.getClientById(id);
    }

    public List<Client> getClientByIds(String ids) {
        return clientRemoteService.getClientByIds(ids);
    }

    public List<Client> getAllClients() {
        return clientRemoteService.getClientByIds();
    }

    public boolean toggleAvailability() {
        return clientRemoteService.toggleAvailable();
    }
}