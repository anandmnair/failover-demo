package com.trade.demo.deal;

import com.trade.demo.client.Client;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Deal {

    private Long id;

    private String name;

    private Long amount;

    private Long clientId;

    private Client client;
}