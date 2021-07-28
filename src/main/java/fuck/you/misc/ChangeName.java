package fuck.you.misc;

import com.google.common.eventbus.Subscribe;

import fuck.you.FabricReflect;
import fuck.you.InfoLogger;
import fuck.you.event.events.EventChatMessage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class ChangeName
{
	@Subscribe
	public void chatMessage( EventChatMessage event )
	{
		String chatmessage = event.getPacket( ).getChatMessage( );
		String lowercase = chatmessage.toLowerCase( );
		if( !lowercase.startsWith( "-changename" ) &&
			!lowercase.startsWith( ">changename" ) &&
			!lowercase.startsWith( "-name" ) && 
			!lowercase.startsWith( ">name" ) ) return;
		
		event.setCancelled( true );
		
		String[ ] split = chatmessage.split( " " );
		if( split.length > 1 )
		{
			if( MinecraftClient.getInstance( ).getSession( ) != null )
			{
				FabricReflect.writeField( MinecraftClient.getInstance( ).getSession( ), split[ 1 ], "a", "username" );
				InfoLogger.info( "Changed name to " + split[ 1 ] );
				MinecraftClient.getInstance( ).player.networkHandler.getConnection( ).disconnect(
						new LiteralText( "Reconnect" ) );
			}
		}
		else
			InfoLogger.info( "Available ChangeName related commands: -changename <name>" );
	}
}
