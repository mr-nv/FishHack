package fuck.you.loggers;

import com.google.common.eventbus.Subscribe;

import fuck.you.InfoLogger;
import fuck.you.event.events.EventReadPacket;

import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;

public class EndPortal {
	@Subscribe
	public void readPacket( EventReadPacket event )
	{
		if( event.getPacket( ) instanceof WorldEventS2CPacket )
		{
			WorldEventS2CPacket packet = (WorldEventS2CPacket)event.getPacket( );
			if( packet.getEventId( ) == 1038 )
			{
				InfoLogger.info( "End Portal activated. X: " + packet.getPos( ).getX( ) +
					" | Y: " + packet.getPos( ).getY( ) +
					" | Z: " + packet.getPos( ).getZ( ) );
			}
		}
	}
}
