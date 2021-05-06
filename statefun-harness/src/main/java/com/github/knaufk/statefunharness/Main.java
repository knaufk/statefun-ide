package com.github.knaufk.statefunharness;

import com.google.protobuf.Message;
import com.google.protobuf.MoreByteStrings;
import org.apache.flink.statefun.flink.harness.Harness;
import org.apache.flink.statefun.flink.harness.io.SerializableSupplier;
import org.apache.flink.statefun.flink.io.generated.AutoRoutable;
import org.apache.flink.statefun.flink.io.generated.RoutingConfig;
import org.apache.flink.statefun.flink.io.generated.TargetFunctionType;
import org.apache.flink.statefun.sdk.io.EgressIdentifier;
import org.apache.flink.statefun.sdk.io.IngressIdentifier;
import org.apache.flink.statefun.sdk.reqreply.generated.TypedValue;

import java.nio.charset.StandardCharsets;

public class Main {

    public static final IngressIdentifier<Message> STRING_INGRESS_IDENTIFIER = new IngressIdentifier<>(Message.class, "greeter.io", "user-logins");

    public static void main(String[] args) throws Exception {
        Harness harness = new Harness();

        harness.withSupplyingIngress(STRING_INGRESS_IDENTIFIER, new SerializableSupplier<Message>() {
            @Override
            public AutoRoutable get() {
                return AutoRoutable.newBuilder()
                        .setConfig(RoutingConfig.newBuilder().addTargetFunctionTypes(TargetFunctionType.newBuilder().setType("demo").setNamespace("greeter.fns").build()))
                        .setId("Igal")
                        .setPayloadBytes(MoreByteStrings.wrap("Igal".getBytes(StandardCharsets.UTF_8)))
                        .build();
            }
        });

        harness.withPrintingEgress(new EgressIdentifier<>("greeter.io", "user-greetings", TypedValue.class));

        harness.start();
    }
}
