package com.trade.demo.client;

import com.societegenerale.failover.core.payload.splitter.PayloadSplitter;
import com.societegenerale.failover.core.payload.splitter.RecoverContext;
import com.societegenerale.failover.core.payload.splitter.StoreContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("clientAllPayloadSplitter")
public class ClientAllPayloadSplitter implements PayloadSplitter<List<Client>, Client> {

    @Override
    public List<StoreContext<Client>> splitOnStore(StoreContext<List<Client>> contexts) {
        return contexts.getPayload().stream().map(client ->
                 StoreContext.<Client>builder()
                        .failover(contexts.getFailover())
                        .args(List.of(client.getId()))  // single-code args → key derivation
                        .payload(client)
                        .build()
        ).toList();
    }

    @Override
    public List<RecoverContext<Client>> splitOnRecover(RecoverContext<List<Client>> context) {
        return List.of(RecoverContext.<Client>builder()
                .failover(context.getFailover())
                .args(List.of())
                .cause(context.getCause())
                .clazz(Client.class)
                .build());
    }

    @Override
    public RecoverContext<List<Client>> merge(List<RecoverContext<Client>> contexts) {
        return RecoverContext.<List<Client>>builder()
                .failover(contexts.getFirst().getFailover())
                .cause(contexts.getFirst().getCause())
                .args(contexts.stream().flatMap(c -> c.getArgs().stream()).toList())
                .payload(contexts.stream().map(RecoverContext::getPayload).toList())
                .build();
    }
}
