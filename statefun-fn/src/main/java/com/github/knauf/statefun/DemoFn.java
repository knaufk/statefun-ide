package com.github.knauf.statefun;

import org.apache.flink.statefun.sdk.java.Context;
import org.apache.flink.statefun.sdk.java.StatefulFunction;
import org.apache.flink.statefun.sdk.java.StatefulFunctionSpec;
import org.apache.flink.statefun.sdk.java.message.Message;

import java.util.concurrent.CompletableFuture;

import static org.apache.flink.statefun.sdk.java.TypeName.typeNameFromString;

public class DemoFn implements StatefulFunction {

    final static StatefulFunctionSpec SPEC = StatefulFunctionSpec.builder(typeNameFromString("com.knaufk/demo"))
            .withSupplier(DemoFn::new)
            .build();

    @Override
    public CompletableFuture<Void> apply(Context context, Message message) throws Throwable {
        System.out.println("Hello: " + message.asUtf8String());
        return context.done();
    }
}
