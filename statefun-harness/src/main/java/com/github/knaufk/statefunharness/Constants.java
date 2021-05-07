package com.github.knaufk.statefunharness;

import com.google.protobuf.Message;
import org.apache.flink.statefun.flink.core.protorouter.AutoRoutableProtobufRouter;
import org.apache.flink.statefun.sdk.io.EgressIdentifier;
import org.apache.flink.statefun.sdk.io.EgressSpec;
import org.apache.flink.statefun.sdk.io.IngressIdentifier;
import org.apache.flink.statefun.sdk.reqreply.generated.TypedValue;
import org.apache.flink.statefun.sdk.spi.StatefulFunctionModule;

import java.util.Map;

public class Constants {

    public static final IngressIdentifier<Message> IN_MEMORY_TEST_INGRESS = new IngressIdentifier<>(Message.class, "com.github.knaufk.demo","kafka-ingress");
    public static final EgressIdentifier<TypedValue> IN_MEMORY_TEST_EGRESS = new EgressIdentifier("com.github.knaufk.demo","kafka-egress", TypedValue.class);

}
