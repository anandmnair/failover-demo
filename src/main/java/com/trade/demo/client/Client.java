package com.trade.demo.client;

import com.societegenerale.failover.domain.Referential;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client extends Referential {
    private Long id;
    private String name;
    private Long score;
}
