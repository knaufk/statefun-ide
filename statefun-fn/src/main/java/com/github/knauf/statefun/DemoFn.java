package com.github.knauf.statefun;

import org.apache.flink.statefun.sdk.java.Context;
import org.apache.flink.statefun.sdk.java.StatefulFunction;
import org.apache.flink.statefun.sdk.java.message.Message;

import java.util.concurrent.CompletableFuture;

public class DemoFn implements StatefulFunction {

    @Override
    public CompletableFuture<Void> apply(Context context, Message message) throws Throwable {
        System.out.println("Yeah!");
        return context.done();
    }
}
