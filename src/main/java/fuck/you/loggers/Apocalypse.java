package fuck.you.loggers;

import java.net.Proxy;

import com.google.common.eventbus.Subscribe;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import fuck.you.event.events.EventChatMessage;
import fuck.you.event.events.EventReadPacket;
import fuck.you.FabricReflect;
import fuck.you.InfoLogger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public class Apocalypse {
	public boolean toggled = false;
	
	@Subscribe
	public void readPacket( EventReadPacket event )
	{
		if( !toggled ) return;
		
		if( event.getPacket( ) instanceof PlayerPositionLookS2CPacket )
		{
			PlayerPositionLookS2CPacket packet = ( PlayerPositionLookS2CPacket )event.getPacket( );
			
			Integer x = Integer.valueOf( ( int )Math.round( packet.getX( ) ) );
			Integer y = Integer.valueOf( ( int )Math.round( packet.getY( ) ) );
			Integer z = Integer.valueOf( ( int )Math.round( packet.getZ( ) ) );
			
			InfoLogger.info( x.toString( ) + " " + y.toString( ) + " " + z.toString( ) );
		}
	}
	
	@Subscribe
	public void chatMessage( EventChatMessage event )
	{
		String chatmessage = event.getPacket( ).getChatMessage( ).toLowerCase( );
		if( !chatmessage.startsWith( "-apocalypse" ) &&
			!chatmessage.startsWith( ">apocalypse" ) ) return;
		
		/*YggdrasilUserAuthentication auth =
				(YggdrasilUserAuthentication)new YggdrasilAuthenticationService(
					Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);
			
		String email = "kek";
		auth.setUsername(email);
		auth.setPassword("");
			
		FabricReflect.writeField( MinecraftClient.getInstance( ).getSession( ), email, "a", "username" );*/
		//System.out.println( "kek" );
		chatmessage = chatmessage.substring( 1 );
		
		event.setCancelled( true );
		
		switch( chatmessage )
		{
		case "apocalypse":
		case "apocalypse t":
		case "apocalypse toggle":
			toggled = !toggled;
			InfoLogger.info( toggled ? "Apocalypse is now §aENABLED§f. Please rejoin" : "Apocalypse is now §4DISABLED" );
			break;
		default:
			InfoLogger.info( "Available Apocalypse related commands: -apocalypse toggle" );
			break;
		}
	}
}
