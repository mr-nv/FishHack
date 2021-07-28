package fuck.you.misc;

import java.text.DecimalFormat;

import com.google.common.eventbus.Subscribe;

import fuck.you.event.events.EventDrawOverlay;
import fuck.you.event.events.EventReadPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.math.MathHelper;

public class Tickrate {
	public boolean toggled = true; // should print tps?
	
	public static float TPS = 20.0f;

    public static long lastUpdate = -1;

    public static float[] tpsCounts = new float[10];

    public static DecimalFormat format = new DecimalFormat("##.0#");
	
	@Subscribe
	public void readPacket( EventReadPacket event )
	{
		if( event.getPacket( ) instanceof WorldTimeUpdateS2CPacket )
		{
			long currentTime = System.currentTimeMillis();

	        if (lastUpdate == -1) {
	            lastUpdate = currentTime;
	            return;
	        }
	        long timeDiff = currentTime - lastUpdate;
	        float tickTime = timeDiff / 20;
	        if (tickTime == 0) {
	            tickTime = 50;
	        }
	        float tps = 1000 / tickTime;
	        if (tps > 20.0f) {
	            tps = 20.0f;
	        }
	        System.arraycopy(tpsCounts, 0, tpsCounts, 1, tpsCounts.length - 1);
	        tpsCounts[0] = tps;

	        double total = 0.0;
	        for (float f : tpsCounts) {
	            total += f;
	        }
	        total /= tpsCounts.length;

	        if (total > 20.0) {
	            total = 20.0;
	        }

	        TPS = Float.parseFloat(format.format(total));
	        lastUpdate = currentTime;
		}
	}
	
	@Subscribe
	public void drawScreen( EventDrawOverlay event )
	{
		if( toggled )
		{
			/*MinecraftClient client = MinecraftClient.getInstance( );
			if( client == null ) return;
			
			int x = client.getWindow( ).getWidth( ) / 4;
			String string = String.format( "TPS: %.2f", TPS );
			int width = client.textRenderer.getStringWidth( string );
			
			client.textRenderer.drawWithShadow( string, x - ( width / 2 ), 5, -1 );*/
		}
	}
}
