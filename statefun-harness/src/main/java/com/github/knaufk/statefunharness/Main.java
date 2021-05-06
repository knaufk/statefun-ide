package com.github.knaufk.statefunharness;

import com.google.protobuf.Message;
import org.apache.flink.statefun.flink.harness.Harness;
import org.apache.flink.statefun.flink.harness.io.SerializableSupplier;
import org.apache.flink.statefun.sdk.io.IngressIdentifier;

import java.util.List;

import static com.github.knaufk.statefunharness.Utils.HarnessRemoteMessage.ofUtf8String;
import static java.util.Arrays.asList;

public class Main {

    public static final IngressIdentifier<Message> IN_MEMORY_TEST_INGRESS = new IngressIdentifier<>(Message.class, "com.knaufk", "demo");

    public static final class CyclingInMemoryIngress implements SerializableSupplier<Message> {

        private final List<Message> items;
        private int index;

        public CyclingInMemoryIngress(List<Message> items) {
            this.items = items;
            this.index = 0;
        }

        @Override
        public Message get() {
            Utils.sleepALittle();
            this.index = (this.index + 1) % items.size();
            return items.get(index);
        }
    }

    public static void main(String[] args) throws Exception {
        Harness harness = new Harness();
        harness.withSupplyingIngress(IN_MEMORY_TEST_INGRESS, new CyclingInMemoryIngress(asList(
                ofUtf8String("com.knaufk/demo", "foo", "Hello"),
                ofUtf8String("com.knaufk/demo", "bar", "World"),
                ofUtf8String("com.knaufk/demo", "baz", "!")
        )));
        harness.start();
    }
}
