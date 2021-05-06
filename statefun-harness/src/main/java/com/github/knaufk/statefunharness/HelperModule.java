package com.github.knaufk.statefunharness;

import com.google.protobuf.Message;
import org.apache.flink.statefun.flink.core.protorouter.AutoRoutableProtobufRouter;
import org.apache.flink.statefun.sdk.io.IngressIdentifier;
import org.apache.flink.statefun.sdk.spi.StatefulFunctionModule;

import java.util.Map;

public class HelperModule implements StatefulFunctionModule {

    public static final IngressIdentifier<Message> IN_MEMORY_TEST_INGRESS = new IngressIdentifier<>(Message.class, "com.knaufk", "demo");

    @Override
    public void configure(Map<String, String> map, Binder binder) {
        binder.bindIngressRouter(IN_MEMORY_TEST_INGRESS, new AutoRoutableProtobufRouter());
    }
}
