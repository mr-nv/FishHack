package fuck.you.misc;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.google.common.eventbus.Subscribe;

import fuck.you.InfoLogger;
import fuck.you.event.events.EventChatMessage;

public class CustomChat
{
	public boolean toggled = false;
	private static final String customstr = " » ʌгᴇѕ+ « ᴋᴀᴍɪ ʙʟᴜᴇ ᴏɴ ᴛᴏᴘ » » ˢⁿᵒʷ⏐ НεᎮнᗩεѕƭυѕ » ʙᴀᴄᴋᴅᴏᴏʀᴇᴅ⏐ ᴍᴇᴏᴡ » ᴜɴɪᴄᴏʀɴɢᴏᴅ.ɢɢ ~~⏐ ꜱᴇᴘᴘᴜᴋᴜ ⏐ ʜᴜᴢᴜɴɪɢʀᴇᴇɴ.ɢɢ™ »⏐ ʙᴀᴄᴋᴄʟɪᴇɴᴛ™ » ɴᴏᴜ ʟᴇᴀᴋ yin_yang <> ⏐ ғᴏʀɢᴇʀᴀᴛ ♡ | ӨBΛMΛ ᄃᄂIΣПƬ";
	
	@Subscribe
	public void chatMessage( EventChatMessage event ) throws IllegalAccessException
	{
		if( event.isCancelled( ) ) return;
		
		String chatmessage = event.getPacket( ).getChatMessage( );
		String lowercase = chatmessage.toLowerCase( );
		if( lowercase.startsWith( ">customchat" ) ||
			lowercase.startsWith( "-customchat" ) )
		{
			event.setCancelled( true );
			
			toggled = !toggled;
			InfoLogger.info( toggled ? "CustomChat is now §aENABLED" : "CustomChat is now §4DISABLED" );
		}
		else
		{
			if( toggled )
			{
				chatmessage += customstr;
				if( chatmessage.length( ) > 256 )
					chatmessage = chatmessage.substring( 0, 256 );
				
				FieldUtils.writeField( event.getPacket( ), "chatMessage", chatmessage, true );
			}
		}
	}
}
