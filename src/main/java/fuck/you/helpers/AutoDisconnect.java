package fuck.you.helpers;

import com.google.common.eventbus.Subscribe;

import fuck.you.InfoLogger;
import fuck.you.event.events.EventChatMessage;
import fuck.you.event.events.EventDrawOverlay;
import fuck.you.event.events.EventTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.network.packet.c2s.play.PickFromInventoryC2SPacket;

public class AutoDisconnect {
	public boolean toggled = true;
	private long lastdeath = 0;
	private boolean disconnected = false;
	private boolean died = false;
	public boolean autorespawn = false;
	private long deathtime = 0;
	public long delay = 0;
	
	@Subscribe
	public void drawScreen( EventDrawOverlay event )
	{
		if( !toggled ) return;
		
		MinecraftClient client = MinecraftClient.getInstance( );
		if( client == null ) return;
		
		if( !disconnected )
		{
			if( client.currentScreen instanceof DeathScreen ||
				client.player.getHealth( ) <= 0 )
			{
				if( !died )
				{
					died = true;
					deathtime = System.currentTimeMillis( );
				}
				else
				{
					if( System.currentTimeMillis( ) >= ( deathtime + delay ) )
					{
						died = false;
						deathtime = 0;
						lastdeath = 0;
						disconnected = true;
						InfoLogger.info( "Disconnecting due to player death" );
						for( int i = 0; i < 10; i++ )
						{
							client.player.networkHandler.sendPacket( new PickFromInventoryC2SPacket( 1337 ) );
							client.player.networkHandler.getConnection( ).disconnect( new LiteralText( "Disconnected due to player death" ) );
						}
					}
				}
			}
		}
		else
		{
			if( client.player == null ) return;
			
			if( client.player.getHealth( ) > 0 )
			{
				if( lastdeath == 0 )
					lastdeath = System.currentTimeMillis( );
				
				if( System.currentTimeMillis( ) > ( lastdeath + 3000 ) )
					disconnected = false;
			}
			else
			{
				if( autorespawn )
					client.player.requestRespawn( );
			}
		}
	}
	
	@Subscribe
	public void chatMessage( EventChatMessage event )
	{
		String chatmessage = event.getPacket( ).getChatMessage( ).toLowerCase( );
		if( !chatmessage.startsWith( "-autodisconnect" ) &&
			!chatmessage.startsWith( ">autodisconnect" ) ) return;
		
		event.setCancelled( true );
		
		chatmessage = chatmessage.substring( 1 );
		
		if( chatmessage.startsWith( "autodisconnect delay " ) )
		{
			chatmessage = chatmessage.replaceAll( "[^\\.0123456789]", "" );
			delay = Math.abs( Long.parseLong( chatmessage ) );
			InfoLogger.info( "AutoDisconnect Delay is now " + delay + " milliseconds" );
			return;
		}
		
		switch( chatmessage )
		{
		case "autodisconnect respawn":
		case "autodisconnect respawn toggle":
		case "autodisconnect autorespawn":
		case "autodisconnect autorespawn toggle":
			autorespawn = !autorespawn;
			InfoLogger.info( autorespawn ? "AutoDisconnect AutoRespawn is now §aENABLED" : "AutoDisconnect is now §4DISABLED" );
			break;
		case "autodisconnect":
		case "autodisconnect toggle":
			toggled = !toggled;
			InfoLogger.info( toggled ? "AutoDisconnect is now §aENABLED" : "AutoDisconnect is now §4DISABLED" );
			break;
		default:
			InfoLogger.info( "Available AutoDisconnect related commands: autodisconnect toggle/autorespawn toggle" );
			break;
		}
	}
}
