package com.trade.demo.client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Client management and availability control")
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/{id}")
    @Operation(summary = "Get client by ID")
    public Client getClientById(
            @Parameter(description = "Client ID (1-10)") @PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @GetMapping("/in/{ids}")
    @Operation(summary = "Get clients by IDs", description = "Comma-separated Long IDs")
    public ClientsResponse getClientsByIds(
            @Parameter(description = "Comma-separated client IDs") @PathVariable String ids) {
        return new ClientsResponse(clientService.getClientByIds(ids));
    }

    @GetMapping
    @Operation(summary = "Get all clients")
    public ClientsResponse getAllClients() {
        return new ClientsResponse(clientService.getAllClients());
    }

    @PostMapping("/toggle")
    @Operation(summary = "Toggle client service availability",
               description = "Toggles the remote client service on/off; returns new availability state")
    @ApiResponse(responseCode = "200", description = "true = available, false = unavailable")
    public boolean toggleAvailability() {
        return clientService.toggleAvailability();
    }
}
