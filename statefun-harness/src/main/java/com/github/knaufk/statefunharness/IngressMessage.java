package com.github.knaufk.statefunharness;

import com.google.protobuf.Message;
import com.google.protobuf.MoreByteStrings;
import org.apache.flink.statefun.flink.common.json.NamespaceNamePair;
import org.apache.flink.statefun.flink.io.generated.AutoRoutable;
import org.apache.flink.statefun.flink.io.generated.RoutingConfig;
import org.apache.flink.statefun.flink.io.generated.TargetFunctionType;

import java.nio.charset.StandardCharsets;

public final class IngressMessage {

    public static Message ofUtf8String(String targetFunctionType, String targetFunctionId, String utf8Value) {
        return ofCustomType(targetFunctionType, targetFunctionId, "io.statefun.types/string", utf8Value.getBytes(StandardCharsets.UTF_8));
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
