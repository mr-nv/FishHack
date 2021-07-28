package fuck.you.event.events;

import fuck.you.event.Event;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

public class EventChatMessage extends Event {
	private ChatMessageC2SPacket packet;

    public EventChatMessage(ChatMessageC2SPacket packet){
        this.packet = packet;
    }

    public ChatMessageC2SPacket getPacket() {
        return packet;
    }

    public void setPacket(ChatMessageC2SPacket packet) {
        this.packet = packet;
    }
}
