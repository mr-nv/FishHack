package fuck.you.event.events;

import fuck.you.event.Event;

public class EventDrawOverlay extends Event {
	private float partialTicks;
	
	public EventDrawOverlay( float partialTicks )
	{
        this.partialTicks = partialTicks;
    }

	public float getPartialTicks( )
	{
		return this.partialTicks;
	}
}