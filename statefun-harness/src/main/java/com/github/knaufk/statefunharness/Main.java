package com.github.knaufk.statefunharness;

import com.github.knaufk.statefunharness.Utils.CyclingInMemoryIngress;
import com.google.protobuf.Message;
import org.apache.flink.statefun.flink.harness.Harness;

import java.util.List;

import static com.github.knaufk.statefunharness.Utils.HarnessRemoteMessage.ofUtf8String;
import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) throws Exception {
        List<Message> inputMessages = asList(
                ofUtf8String("com.knaufk/demo", "foo", "Hello"),
                ofUtf8String("com.knaufk/demo", "bar", "World"),
                ofUtf8String("com.knaufk/demo", "baz", "!")
        );

        new Harness()
                .withSupplyingIngress(Constants.IN_MEMORY_TEST_INGRESS, new CyclingInMemoryIngress(inputMessages))
                .withPrintingEgress(Constants.IN_MEMORY_TEST_EGRESS)
                .start();
    }
}
