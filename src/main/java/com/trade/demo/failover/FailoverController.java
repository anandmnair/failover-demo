package com.trade.demo.failover;

import com.societegenerale.failover.core.payload.ReferentialPayload;
import com.societegenerale.failover.core.store.FailoverStore;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/failover")
@RequiredArgsConstructor
@Tag(name = "failover", description = "Failover management endpoints")
public class FailoverController {

    private final FailoverStore<Object> failoverStore;

    @GetMapping("/{name}/{key}")
    public ReferentialPayload<Object> getByNameAndKey(@PathVariable String name, @PathVariable String key) {
        return failoverStore.find(name, key).orElseThrow(() -> new RuntimeException("Failover data not found for name: " + name + " and key: " + key));
    }
}
