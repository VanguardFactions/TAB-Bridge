package me.neznamy.tab.bridge.shared.placeholder;

import com.vanguardfactions.core.VanguardCore;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.function.BiFunction;

public class RelationalOfflinePlaceholder extends Placeholder {

  /** Last known placeholder values */
  @NotNull
  private final Map<UUID, Map<UUID, String>> lastValues = new WeakHashMap<>();

  /** Placeholder apply function */
  @NotNull
  private final BiFunction<UUID, UUID, String> function;

  public RelationalOfflinePlaceholder(@NonNull String identifier, int refresh, @NotNull BiFunction<UUID, UUID, String> function) {
    super(identifier, refresh);
    this.function = function;
  }

  /**
   * Updates the placeholder for given players. Returns {@code true} if value changed since
   * last time, {@code false} if not.
   *
   * @param   viewer
   *          Player looking at the placeholder value
   * @param   target
   *          Player the value is displayed on
   * @return  {@code true} if value changed, {@code false} if not
   */
  public boolean update(@NonNull UUID viewer, @NonNull UUID target) {
    String value = request(viewer, target);
    if (!lastValues.computeIfAbsent(viewer, v -> new WeakHashMap<>()).getOrDefault(target, getIdentifier()).equals(value)) {
      lastValues.get(viewer).put(target, value);
      return true;
    }
    return false;
  }

  /**
   * Requests new value for the players and returns it. If the call threw an error, it is forwarded
   * to the proxy and {@code <PlaceholderAPI Error>} is returned.
   *
   * @param   viewer
   *          Player looking at the placeholder value
   * @param   target
   *          Player the value is displayed on
   * @return  New value for the players
   */
  @NotNull
  public String request(@NonNull UUID viewer, @NonNull UUID target) {
    try {
      return function.apply(viewer, target);
    } catch (Throwable t) {
      VanguardCore.getLogger().error(
          "Error while setting relational placeholder {} for viewer {} and target {}", identifier, viewer, target, t
      );
      return "<PlaceholderAPI Error>";
    }
  }

  /**
   * Returns last known value for players. If not initialized yet, it is calculated using
   * provided apply function and then returned.
   *
   * @param   viewer
   *          Player looking at the placeholder value
   * @param   target
   *          Player the value is displayed on
   * @return  Last known value of the placeholder for players
   */
  @NotNull
  public String getLastValue(@NonNull UUID viewer, @NonNull UUID target) {
    return lastValues.computeIfAbsent(viewer, v -> new WeakHashMap<>()).computeIfAbsent(target, t -> request(viewer, target));
  }

}
