package com.vanguardfactions.tab;

import lombok.*;
import me.neznamy.tab.bridge.shared.message.outgoing.UpdateRelationalPlaceholder;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceholderUpdateRequest {

  private UUID playerId;
  private List<UpdateRelationalPlaceholder> updates;

}
