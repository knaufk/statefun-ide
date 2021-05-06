/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.knauf.statefun;

import io.undertow.Undertow;
import org.apache.flink.statefun.sdk.java.StatefulFunctions;
import org.apache.flink.statefun.sdk.java.handler.RequestReplyHandler;

public final class Main {

    public static void main(String[] args) {
        StatefulFunctions functions = new StatefulFunctions();
        // here is the place to define any functions served with this endpoint.
        // in this demo, we are only serving a single function:
        functions.withStatefulFunction(DemoFn.SPEC);

        // The following section is a boilerplate to serve this endpoint.
        // This example uses the Undertow HTTP server, but any other HTTP server (Jetty, Tomcat, Netty, etc') will do:
        final RequestReplyHandler requestReplyHandler = functions.requestReplyHandler();
        final Undertow httpServer =
                Undertow.builder()
                        .addHttpListener(1108, "0.0.0.0")
                        .setHandler(new UndertowHttpHandler(requestReplyHandler))
                        .build();
        httpServer.start();
    }
}
