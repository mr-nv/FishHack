package fuck.you.helpers;

import com.google.common.eventbus.Subscribe;

import fuck.you.InfoLogger;
import fuck.you.event.events.EventTick;
import fuck.you.event.events.EventChatMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Vec3d;

public class BoatFly {
	public boolean toggled = true;
	public double upspeed = 2;
	public double downspeed = 0.0;
	
	@Subscribe
	public void onTick( EventTick event )
	{
		if( !toggled ) return;
		
		MinecraftClient client = MinecraftClient.getInstance( );
		if( client == null || client.player == null ) return;
		
		Entity vehicle = client.player.getVehicle( );
		//if( vehicle == null || !( vehicle instanceof BoatEntity ) ||
			//vehicle.getPassengerList( ) == null ) return;
		if( vehicle == null ) return; //vehicle.getPassengerList( ).get( 0 ) != client.player ) return;

		vehicle.yaw = client.player.yaw;
		
		Vec3d velocity = vehicle.getVelocity( );
		vehicle.setVelocity(
				velocity.x,
				( client.options.keyJump.isPressed( ) ) ? upspeed : -downspeed,
				velocity.z );
	}
	
	@Subscribe
	public void chatMessage( EventChatMessage event )
	{
		String chatmessage = event.getPacket( ).getChatMessage( ).toLowerCase( );
		if( !chatmessage.startsWith( "-boatfly" ) &&
			!chatmessage.startsWith( ">boatfly" ) ) return;
		
		event.setCancelled( true );
		
		chatmessage = chatmessage.substring( 1 );
		
		if( chatmessage.equals( "boatfly toggle" ) ||
			chatmessage.equals( "bf toggle" ) )
		{
			toggled = !toggled;
			InfoLogger.info( toggled ? "BoatFly is now §aENABLED" : "BoatFly is now §4DISABLED" );
		}
		else
		{
			if( chatmessage.startsWith( "boatfly upspeed " ) ||
				chatmessage.startsWith( "bf upspeed " ) )
			{
				chatmessage = chatmessage.replaceAll( "[^\\.0123456789]", "" );
				upspeed = Math.abs( Double.parseDouble( chatmessage ) );
				InfoLogger.info( "BoatFly UpSpeed is now " + upspeed );
			}
			else if( chatmessage.startsWith( "boatfly downspeed " ) ||
					chatmessage.startsWith( "bf downspeed " ) )
			{
				chatmessage = chatmessage.replaceAll( "[^\\.0123456789]", "" );
				downspeed = Math.abs( Double.parseDouble( chatmessage ) );
				InfoLogger.info( "BoatFly DownSpeed is now " + downspeed );
			}
			else
			{
				InfoLogger.info( "Available BoatFly related commands: boatfly toggle/upspeed/downspeed" );
			}
		}
	}
}
