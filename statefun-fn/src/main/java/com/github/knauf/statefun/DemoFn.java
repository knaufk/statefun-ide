package com.github.knauf.statefun;

import org.apache.flink.statefun.sdk.java.Context;
import org.apache.flink.statefun.sdk.java.StatefulFunction;
import org.apache.flink.statefun.sdk.java.StatefulFunctionSpec;
import org.apache.flink.statefun.sdk.java.TypeName;
import org.apache.flink.statefun.sdk.java.io.KafkaEgressMessage;
import org.apache.flink.statefun.sdk.java.message.Message;

import java.util.concurrent.CompletableFuture;

import static org.apache.flink.statefun.sdk.java.TypeName.typeNameFromString;

public class DemoFn implements StatefulFunction {

    final static StatefulFunctionSpec SPEC = StatefulFunctionSpec.builder(typeNameFromString("com.knaufk/demo"))
            .withSupplier(DemoFn::new)
            .build();

    @Override
    public CompletableFuture<Void> apply(Context context, Message message) {
        System.out.println("Hello from " + context.self().id() + ", I've received a message: " + message.asUtf8String());
        context.send(KafkaEgressMessage.forEgress(TypeName.typeNameOf("com.github.knaufk.demo","kafka-egress"))
                .withUtf8Key(context.self().id())
                .withUtf8Value(message.asUtf8String())
                .withTopic("output")
                .build());
        return context.done();
    }
}
