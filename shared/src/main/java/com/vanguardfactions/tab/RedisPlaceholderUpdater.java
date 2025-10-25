package com.vanguardfactions.tab;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vanguardfactions.core.VanguardCore;
import me.neznamy.tab.bridge.shared.TABBridge;
import me.neznamy.tab.bridge.shared.message.outgoing.UpdateRelationalPlaceholder;
import org.jetbrains.annotations.NotNull;
import org.redisson.api.RTopic;
import org.redisson.codec.JsonJacksonCodec;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class RedisPlaceholderUpdater {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private static RTopic topic;

  public static void init() {
    topic = VanguardCore.getRedisService()
        .client()
        .getTopic(TABBridge.CHANNEL_NAME, new JsonJacksonCodec(MAPPER));
  }

  public static void update(final @NotNull UUID playerId, final @NotNull List<UpdateRelationalPlaceholder> updates) {
    final PlaceholderUpdateRequest request = new PlaceholderUpdateRequest(playerId, updates);
    CompletableFuture.runAsync(() -> topic.publish(request))
            .exceptionally(throwable -> {
              VanguardCore.getLogger().error("[TAB Bridge] Failed to publish placeholder update", throwable);
              return null;
            });
  }
}
