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

    public List<Client> getClientByIds(List<Long> ids) {
        return clientRemoteService.getClientByIds(ids);
    }

    public List<Client> getClientByStringIds(String ids) {
        return clientRemoteService.getClientByStringIds(ids);
    }

    public List<Client> getAllClients() {
        return clientRemoteService.getAllClients();
    }

    public boolean toggleAvailability() {
        return clientRemoteService.toggleAvailable();
    }
}