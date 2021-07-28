package fuck.you.loggers;

import com.google.common.eventbus.Subscribe;

import fuck.you.InfoLogger;
import fuck.you.event.events.EventReadPacket;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class Thunder {
	@Subscribe
	public void readPacket( EventReadPacket event )
	{
		
		if( event.getPacket( ) instanceof PlaySoundS2CPacket )
		{
			PlaySoundS2CPacket packet = ( PlaySoundS2CPacket )event.getPacket( );
			
			//if( packet.getSound().getId().toString().toLowerCase().contains("light"))
			//InfoLogger.info(packet.getSound().getId().toString());
			
			if( packet.getCategory( ) == SoundCategory.WEATHER && 
				( packet.getSound( ) == SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER ) )
			{
				double x = packet.getX( ) - MinecraftClient.getInstance( ).player.getX( );
				double z = packet.getZ( ) - MinecraftClient.getInstance( ).player.getZ( );
				
				float yaw = 0;
				yaw += MathHelper.wrapDegrees( ( Math.toDegrees( Math.atan2( z, x ) ) - 90.0f ) - yaw );
				
				String myx = String.format( "%.4f", MinecraftClient.getInstance( ).player.getX( ) );
				String myz = String.format( "%.4f", MinecraftClient.getInstance( ).player.getZ( ) );
				
				InfoLogger.info( "Lightning Bolt spawned. X: " + myx +
						" | Z: " + myz +
						" | Angle: " + yaw +
						" (" + MathHelper.floor( packet.getX( ) ) + " " + MathHelper.floor( packet.getZ( ) ) + ")" );
			}
		}
	}
}
