package fuck.you.helpers;

import com.google.common.eventbus.Subscribe;

import fuck.you.FishHack;
import fuck.you.InfoLogger;
import fuck.you.event.events.EventChatMessage;
import fuck.you.event.events.EventDrawOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.PickFromInventoryC2SPacket;
import net.minecraft.text.LiteralText;

public class AutoDupe {
	private boolean toggled = false;
	public String password = "";
	private boolean oldautodisconnect = false;
	
	private boolean shoulddie = false;
	private boolean shouldleave = false;
	private boolean disconnected = false;
	private long timer = 0;
	private boolean loggedin = false;
	public long disconnectdelay = 750;
	public long logindelay = 800;
	
	@Subscribe
	public void drawScreen( EventDrawOverlay event )
	{
		if( !toggled ) return;
		
		MinecraftClient client = MinecraftClient.getInstance( );
		if( client == null ||
			client.player == null ) return;
		if( password.isEmpty( ) ) return;
		
		if( FishHack.helperAutoDisconnect.toggled )
			FishHack.helperAutoDisconnect.toggled = false;
		
		if( client.player.getHealth( ) > 0 && shoulddie )
		{
			client.player.networkHandler.sendPacket(
					new ChatMessageC2SPacket( "/kill" ) );
			
			shoulddie = false;
		}
		
		if( !disconnected )
		{
			if( client.currentScreen instanceof DeathScreen ||
				client.player.getHealth( ) <= 0 )
			{
				if( timer == 0 )
				{
					shouldleave = true;
					timer = System.currentTimeMillis( );
				}
			}
			
			if( shouldleave )
			{
				if( System.currentTimeMillis( ) > ( timer + disconnectdelay ) )
				{
					for( int i = 0; i < 10; i++ )
						client.player.networkHandler.getConnection( ).disconnect( new LiteralText( "[AutoDupe] Disconnecting. Please wait 5 seconds before joining back" ) );
					
					shouldleave = false;
					disconnected = true;
					timer = 0;
				}
			}
		}
		else
		{
			if( client.player.getHealth( ) > 0 )
			{
				if( timer == 0 )
					timer = System.currentTimeMillis( );
				
				if( !loggedin )
				{
					if( System.currentTimeMillis( ) > ( timer + logindelay ) )
					{
						client.player.networkHandler.sendPacket(
								new ChatMessageC2SPacket( "/login " + password ) );
						
						timer = System.currentTimeMillis( );
						loggedin = true;
					}
				}
				else
				{
					if( System.currentTimeMillis( ) > ( timer + 2500 ) )
					{
						// its time to do some shit again
						loggedin = false;
						disconnected = false;
						shoulddie = true;
						timer = 0;
					}
				}
			}
			else
				client.player.requestRespawn( );
		}
	}
	
	@Subscribe
	public void chatMessage( EventChatMessage event )
	{
		String chatmessage = event.getPacket( ).getChatMessage( );
		if( !chatmessage.toLowerCase( ).startsWith( "-autodupe" ) &&
			!chatmessage.toLowerCase( ).startsWith( "-dupe" ) &&
			!chatmessage.toLowerCase( ).startsWith( ">autodupe" ) &&
			!chatmessage.toLowerCase( ).startsWith( ">dupe" ) ) return;
		
		event.setCancelled( true );
		
		chatmessage = chatmessage.substring( 1 );
		
		if( chatmessage.toLowerCase( ).startsWith( "dupe password " ) ||
			chatmessage.toLowerCase( ).startsWith( "autodupe password " ) )
		{
			String[ ] splitted = chatmessage.split( " " );
			if( splitted[ 2 ] != null &&
				!splitted[ 2 ].isEmpty( ) )
			{
				password = splitted[ 2 ];
				InfoLogger.info( "Updated AutoDupe password" );
				shoulddie = true;
			}
			
			return;
		}
		else if( chatmessage.toLowerCase( ).startsWith( "dupe logindelay " ) ||
				chatmessage.toLowerCase( ).startsWith( "autodupe logindelay " ) )
		{
			chatmessage = chatmessage.replaceAll( "[^\\.0123456789]", "" );
			logindelay = Math.abs( Long.parseLong( chatmessage ) );
			InfoLogger.info( "AutoDupe LoginDelay is now " + logindelay + " milliseconds" );
			return;
		}
		else if( chatmessage.toLowerCase( ).startsWith( "dupe disconnectdelay " ) ||
				chatmessage.toLowerCase( ).startsWith( "autodupe disconnectdelay " ) )
		{
			chatmessage = chatmessage.replaceAll( "[^\\.0123456789]", "" );
			disconnectdelay = Math.abs( Long.parseLong( chatmessage ) );
			InfoLogger.info( "AutoDupe DisconnectDelay is now " + disconnectdelay + " milliseconds" );
			return;
		}
		
		switch( chatmessage.toLowerCase( ) )
		{
		case "dupe toggle":
		case "autodupe toggle":
			toggled = !toggled;
			InfoLogger.info( toggled ? "AutoDupe is now §aENABLED" : "AutoDupe is now §4DISABLED" );
			if( password.isEmpty( ) )
				InfoLogger.info( "NOTE: Please add your password using \"autodupe password <input>\"");
			
			if( !toggled )
				FishHack.helperAutoDisconnect.toggled = oldautodisconnect;
			else
				oldautodisconnect = FishHack.helperAutoDisconnect.toggled;
			
			break;
		default:
			InfoLogger.info( "Available AutoDupe commands: autodupe toggle/password" );
			break;
		}
	}
}
