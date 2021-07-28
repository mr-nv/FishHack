package fuck.you.helpers;

import com.google.common.eventbus.Subscribe;

import java.lang.Thread;

import de.roth.json.config.Config;
import fuck.you.FishHack;
import fuck.you.InfoLogger;
import fuck.you.event.events.EventChatMessage;
import fuck.you.event.events.EventDrawOverlay;

public class ConfigHelper {
	private long lastsave = 0;
	private String lastsavestr = "";
	
	public void saveConfig( )
	{
		Config.getInstance( ).autodisconnect_toggled = FishHack.helperAutoDisconnect.toggled;
		Config.getInstance( ).autodisconnect_autorespawn = FishHack.helperAutoDisconnect.autorespawn;
		Config.getInstance( ).autodisconnect_delay = FishHack.helperAutoDisconnect.delay;
		Config.getInstance( ).autodupe_disconnectdelay = FishHack.helperAutoDupe.disconnectdelay;
		Config.getInstance( ).autodupe_logindelay = FishHack.helperAutoDupe.logindelay;
		Config.getInstance( ).autodupe_password = FishHack.helperAutoDupe.password;
		Config.getInstance( ).blockdirection = FishHack.helperBlockDirection.direction;
		Config.getInstance( ).boatfly_toggled = FishHack.helperBoatFly.toggled;
		Config.getInstance( ).boatfly_upspeed = FishHack.helperBoatFly.upspeed;
		Config.getInstance( ).boatfly_downspeed = FishHack.helperBoatFly.downspeed;
		Config.getInstance( ).customchat_toggled = FishHack.miscCustomChat.toggled;
		
		Config.getInstance( ).toFile( "fishhack.json" );
	}
	
	public void loadConfig( )
	{
		FishHack.helperAutoDisconnect.toggled = Config.getInstance( ).autodisconnect_toggled;
		FishHack.helperAutoDisconnect.autorespawn = Config.getInstance( ).autodisconnect_autorespawn;
		FishHack.helperAutoDisconnect.delay = Config.getInstance( ).autodisconnect_delay;
		FishHack.helperAutoDupe.disconnectdelay = Config.getInstance( ).autodupe_disconnectdelay;
		FishHack.helperAutoDupe.logindelay = Config.getInstance( ).autodupe_logindelay;
		FishHack.helperAutoDupe.password = Config.getInstance( ).autodupe_password;
		FishHack.helperBlockDirection.direction = Config.getInstance( ).blockdirection;
		FishHack.helperBoatFly.toggled = Config.getInstance( ).boatfly_toggled;
		FishHack.helperBoatFly.upspeed = Config.getInstance( ).boatfly_upspeed;
		FishHack.helperBoatFly.downspeed = Config.getInstance( ).boatfly_downspeed;
		FishHack.miscCustomChat.toggled = Config.getInstance( ).customchat_toggled;
	}
	
	@Subscribe
	public void chatMessage( EventChatMessage event )
	{
		String chatmessage = event.getPacket( ).getChatMessage( ).toLowerCase( );
		if( !chatmessage.startsWith( "-config" ) &&
			!chatmessage.startsWith( ">config" ) ) return;
		
		event.setCancelled( true );
		
		chatmessage = chatmessage.substring( 1 );
		
		switch( chatmessage )
		{
		case "config save":
			InfoLogger.info( "Saving configuration to fishhack.json" );
			saveConfig( );
			break;
		case "config load":
			InfoLogger.info( "Loading configuration from fishhack.json" );
			loadConfig( );
			break;
		default:
			InfoLogger.info( "Available Config related commands: config save/load" );
			break;
		}
	}
	
	public void createThread( )
	{
		new Thread( ( ) ->
		{
			while( true )
			{
				if( System.currentTimeMillis( ) > ( lastsave + 15000 ) )
				{
					if( Config.getInstance( ).toString( ) != lastsavestr )
					{
						saveConfig( );
						lastsavestr = Config.getInstance( ).toString( );
					}
					
					lastsave = System.currentTimeMillis( );
				}
			}
		} ).start( );
	}
}
