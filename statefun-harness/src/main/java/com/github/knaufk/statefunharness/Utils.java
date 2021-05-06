package com.github.knaufk.statefunharness;

import com.google.protobuf.Message;
import com.google.protobuf.MoreByteStrings;
import com.google.protobuf.StringValue;
import org.apache.flink.statefun.flink.common.json.NamespaceNamePair;
import org.apache.flink.statefun.flink.harness.io.SerializableSupplier;
import org.apache.flink.statefun.flink.io.generated.AutoRoutable;
import org.apache.flink.statefun.flink.io.generated.RoutingConfig;
import org.apache.flink.statefun.flink.io.generated.TargetFunctionType;

import java.util.List;

final class Utils {

    static void sleepALittle() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException("bye bye", e);
        }
    }

    public static final class HarnessRemoteMessage {

        public static Message ofUtf8String(String targetFunctionType, String targetFunctionId, String utf8Value) {
            return ofCustomType(targetFunctionType, targetFunctionId, "io.statefun.types/string", StringValue.of(utf8Value).toByteArray());
        }

        public static Message ofCustomType(String targetFunctionType, String targetFunctionId, String valueTypeUrl, byte[] value) {
            RoutingConfig.Builder config = RoutingConfig.newBuilder()
                    .addTargetFunctionTypes(parseFnType(targetFunctionType))
                    .setTypeUrl(valueTypeUrl);

            return AutoRoutable.newBuilder()
                    .setConfig(config)
                    .setId(targetFunctionId)
                    .setPayloadBytes(MoreByteStrings.wrap(value))
                    .build();
        }

        private static TargetFunctionType parseFnType(String targetFunctionType) {
            NamespaceNamePair nn = NamespaceNamePair.from(targetFunctionType);
            return TargetFunctionType.newBuilder()
                    .setType(nn.name())
                    .setNamespace(nn.namespace())
                    .build();
        }


    }

    public static final class CyclingInMemoryIngress implements SerializableSupplier<Message> {

        private final List<Message> items;
        private int index;

        public CyclingInMemoryIngress(List<Message> items) {
            this.items = items;
            this.index = 0;
        }

        @Override
        public Message get() {
            sleepALittle();
            this.index = (this.index + 1) % items.size();
            return items.get(index);
        }
    }
}
