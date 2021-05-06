package com.github.knaufk.statefunharness;

import com.google.protobuf.Message;
import org.apache.flink.statefun.flink.harness.Harness;
import org.apache.flink.statefun.sdk.io.IngressIdentifier;

public class Main {

    public static final IngressIdentifier<Message> IN_MEMORY_INGRESS = new IngressIdentifier<>(Message.class, "com.knaufk", "demo");

    public static void main(String[] args) throws Exception {
        Harness harness = new Harness();

        harness.withSupplyingIngress(IN_MEMORY_INGRESS, () ->
                IngressMessage.ofUtf8String("com.knaufk/demo", "igal", "Hello world!"));

        harness.start();
    }
}
