package com.trade.demo.client;

import com.societegenerale.failover.core.payload.splitter.PayloadSplitter;
import com.societegenerale.failover.core.payload.splitter.RecoverContext;
import com.societegenerale.failover.core.payload.splitter.StoreContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Component("clientPayloadSplitter")
public class ClientPayloadSplitter implements PayloadSplitter<List<Client>, Client> {

    @Override
    public List<StoreContext<Client>> splitOnStore(StoreContext<List<Client>> contexts) {
        //for comma separated ids
        String[] ids = ((String) contexts.getArgs().getFirst()).split(",");
        List<Client> countries = contexts.getPayload();
        return IntStream.range(0, countries.size())
                .mapToObj(i -> StoreContext.<Client>builder()
                        .failover(contexts.getFailover())
                        .args(List.of(ids[i].trim()))  // single-code args → key derivation
                        .payload(countries.get(i))
                        .build())
                .toList();
    }

    @Override
    public List<RecoverContext<Client>> splitOnRecover(RecoverContext<List<Client>> context) {
        // for comma separated ids
        String ids = (String) context.getArgs().getFirst();
        return Arrays.stream(ids.split(","))
                .map(id -> RecoverContext.<Client>builder()
                        .failover(context.getFailover())
                        .args(List.of(id.trim()))
                        .clazz(Client.class)
                        .cause(context.getCause())
                        .build())
                .toList();
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
