package com.trade.demo.client;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Client {
    private Long id;
    private String name;
    private Long score;
}
