package com.github.knaufk.statefunharness;

import org.apache.flink.statefun.flink.core.protorouter.AutoRoutableProtobufRouter;
import org.apache.flink.statefun.sdk.spi.StatefulFunctionModule;

import java.util.Map;

public class HarnessModule implements StatefulFunctionModule {

    @Override
    public void configure(Map<String, String> map, Binder binder) {
        binder.bindIngressRouter(Main.IN_MEMORY_INGRESS, new AutoRoutableProtobufRouter());
    }
}
