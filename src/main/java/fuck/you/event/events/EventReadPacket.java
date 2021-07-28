package fuck.you.event.events;

import fuck.you.event.Event;
import net.minecraft.network.Packet;

public class EventReadPacket extends Event {
	private Packet<?> packet;
	
	public EventReadPacket(Packet<?> packet){
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }
}
