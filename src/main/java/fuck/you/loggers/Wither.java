package fuck.you.loggers;

import com.google.common.eventbus.Subscribe;

import fuck.you.InfoLogger;
import fuck.you.event.events.EventReadPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.util.math.MathHelper;

public class Wither {
	@Subscribe
	public void readPacket( EventReadPacket event )
	{
		if( event.getPacket( ) instanceof WorldEventS2CPacket )
		{
			//	InfoLogger.info(MinecraftClient.getInstance().getCurrentServerEntry().version);
			
			WorldEventS2CPacket packet = (WorldEventS2CPacket)event.getPacket( );
			if( packet.getEventId( ) == 1023 )
			{
				//InfoLogger.info(MinecraftClient.getInstance().getCurrentServerEntry().version);
				double x = packet.getPos( ).getX( ) - MinecraftClient.getInstance( ).player.getX( );
				double z = packet.getPos( ).getZ( ) - MinecraftClient.getInstance( ).player.getZ( );
				
				float yaw = 0;
				yaw += MathHelper.wrapDegrees( ( Math.toDegrees( Math.atan2( z, x ) ) - 90.0f ) - yaw );
				
				String myx = String.format( "%.4f", MinecraftClient.getInstance( ).player.getX( ) );
				String myz = String.format( "%.4f", MinecraftClient.getInstance( ).player.getZ( ) );
				
				InfoLogger.info( "Wither spawned. X: " + myx +
						" | Z: " + myz +
						" | Angle: " + yaw +
						" (" + MathHelper.floor( packet.getPos( ).getX( ) ) + " " + MathHelper.floor( packet.getPos( ).getZ( ) ) + ")" );
			}
		}
	}
}
