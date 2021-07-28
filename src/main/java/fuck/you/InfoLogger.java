package fuck.you;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class InfoLogger {
	public static void info( String str ) {
		try {
			System.out.println( "[FishHack] " + str );
			
			MinecraftClient.getInstance( ).inGameHud.getChatHud( )
				.addMessage( new LiteralText( "§8[§9Fish§fHack§8]§f " + str ) );
		} catch( Exception e ) { System.out.println( "[FishHack] " + str ); }
	}
}
