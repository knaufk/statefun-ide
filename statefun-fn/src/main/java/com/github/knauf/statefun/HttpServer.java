package com.github.knauf.statefun;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.flink.statefun.sdk.java.StatefulFunctions;
import org.apache.flink.statefun.sdk.java.handler.RequestReplyHandler;
import org.apache.flink.statefun.sdk.java.slice.Slice;
import org.apache.flink.statefun.sdk.java.slice.Slices;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public final class HttpServer {

    public static void serve(int port, StatefulFunctions functions) {
        // The following section is a boilerplate to serve this endpoint.
        // This example uses the Undertow HTTP server, but any other HTTP server (Jetty, Tomcat, Netty, etc') will do:
        final RequestReplyHandler requestReplyHandler = functions.requestReplyHandler();
        final Undertow httpServer =
                Undertow.builder()
                        .addHttpListener(1108, "0.0.0.0")
                        .setHandler(new HttpServer.UndertowHttpHandler(requestReplyHandler))
                        .build();
        httpServer.start();
    }

    /**
     * A simple Undertow {@link HttpHandler} that delegates requests from StateFun runtime processes to
     * a StateFun {@link RequestReplyHandler}.
     */
    public static final class UndertowHttpHandler implements HttpHandler {
        private final RequestReplyHandler handler;

        public UndertowHttpHandler(RequestReplyHandler handler) {
            this.handler = Objects.requireNonNull(handler);
        }

        @Override
        public void handleRequest(HttpServerExchange exchange) {
            exchange.getRequestReceiver().receiveFullBytes(this::onRequestBody);
        }

        @SuppressWarnings("deprecation")
        private void onRequestBody(HttpServerExchange exchange, byte[] requestBytes) {
            exchange.dispatch();
            CompletableFuture<Slice> future = handler.handle(Slices.wrap(requestBytes));
            future.whenComplete((response, exception) -> onComplete(exchange, response, exception));
        }

        private void onComplete(HttpServerExchange exchange, Slice responseBytes, Throwable ex) {
            if (ex != null) {
                ex.printStackTrace(System.out);
                exchange.getResponseHeaders().put(Headers.STATUS, 500);
                exchange.endExchange();
                return;
            }
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/octet-stream");
            exchange.getResponseSender().send(responseBytes.asReadOnlyByteBuffer());
        }
    }
}
