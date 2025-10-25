package me.neznamy.tab.bridge.shared.message.outgoing;

import com.google.common.io.ByteArrayDataOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class UpdateRelationalPlaceholder implements OutgoingMessage {

    @NonNull
    private String identifier;

    @NonNull
    private String otherPlayer;

    @NonNull
    private String value;

    @Override
    public void write(@NonNull ByteArrayDataOutput out) {
        out.writeUTF(identifier);
        out.writeUTF(otherPlayer);
        out.writeUTF(value);
    }
}
