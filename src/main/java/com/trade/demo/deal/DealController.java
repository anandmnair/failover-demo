package com.trade.demo.deal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deals")
@RequiredArgsConstructor
@Tag(name = "Deals", description = "Deal management endpoints")
public class DealController {

    private final DealService dealService;

    @GetMapping("/{id}")
    @Operation(summary = "Get deal by ID", description = "Returns a single deal enriched with client data")
    public Deal getDealById(
            @Parameter(description = "Deal ID (1-10)") @PathVariable Long id) {
        return dealService.findById(id);
    }

    @GetMapping("/in/{ids}")
    @Operation(summary = "Get deals by IDs", description = "Comma-separated Long IDs; returns enriched deals")
    public DealsResponse getDealsByIds(
            @Parameter(description = "Comma-separated deal IDs") @PathVariable String ids) {
        return new DealsResponse(dealService.findAllByIds(ids));
    }

    @GetMapping
    @Operation(summary = "Get all deals", description = "Returns all deals enriched with client data")
    public DealsResponse getAllDeals() {
        return new DealsResponse(dealService.findAll());
    }
}