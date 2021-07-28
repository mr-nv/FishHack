package fuck.you.misc;

import java.util.ArrayList;

import com.google.common.eventbus.Subscribe;

import fuck.you.InfoLogger;
import fuck.you.event.events.EventChatMessage;
import fuck.you.event.events.EventSendPacket;
import fuck.you.event.events.EventTick;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class XrayBypass
{
	public boolean enabled = false;
	private ArrayList< XRayBlock > blocks = new ArrayList< >( );
	
	@Subscribe
	public void onSendPacket( EventSendPacket event )
	{
		if( !enabled ) return;
		
		
		/*if( event.getPacket( ) instanceof PlayerActionC2SPacket )
		{
			PlayerActionC2SPacket packet = ( PlayerActionC2SPacket )event.getPacket( );
			InfoLogger.info( "PlayerActionC2SPacket.getType -> " + packet.getAction( ).toString( ) );
		}*/
		//InfoLogger.info( event.getPacket( ). );
	}
	
	@Subscribe
	public void onTick( EventTick event )
	{
		if( !enabled ) return;
		
		final MinecraftClient mc = MinecraftClient.getInstance( );
		if( mc == null || mc.player == null ) return;
		
		for( int x = -20; x <= 20; x++ )
		{
			for( int y = -7; y <= 7; y++ )
			{
				for( int z = -20; z <= 20; z++ )
				{
					BlockPos block = new BlockPos( mc.player.getX( ) + x, mc.player.getY( ) + y, mc.player.getZ( ) + z );
					XRayBlock xrayblock = getBlock( block );
				}
			}
		}
		
		for( XRayBlock block : blocks )
		{
			if( !block.didClick( ) )
			{
				PlayerActionC2SPacket packet = new PlayerActionC2SPacket( Action.START_DESTROY_BLOCK, block.getBlockPos( ), Direction.UP );
				mc.getNetworkHandler( ).sendPacket( packet );
				block.setDidClick( true );
				break;
			}
		}
	}
	
	@Subscribe
	public void chatMessage( EventChatMessage event )
	{
		String chatmessage = event.getPacket( ).getChatMessage( );
		if( !chatmessage.toLowerCase( ).startsWith( "-xraybypass" ) &&
			!chatmessage.toLowerCase( ).startsWith( ">xraybypass" ) )
			return;
		
		event.setCancelled( true );
		enabled = !enabled;
		blocks.clear( );
		
		InfoLogger.info( enabled ? "XRayBypass is now §aENABLED" : "XRayBypass is now §4DISABLED" );
	}
	
	public XRayBlock getBlock( BlockPos blockpos )
	{
		for( XRayBlock block : blocks )
		{
			if( block.getBlockPos( ).equals( blockpos ) )
				return block;
		}
		
		XRayBlock block = new XRayBlock( blockpos );
		blocks.add( block );
		return block;
	}
	
	class XRayBlock
	{
		private BlockPos blockpos;
		private boolean clicked = false;
		
		public XRayBlock( BlockPos blockpos )
		{
			this.blockpos = blockpos;
		}
		
		public BlockPos getBlockPos( )
		{
			return blockpos;
		}
		
		public boolean didClick( )
		{
			return clicked;
		}
		
		public void setDidClick( boolean state )
		{
			this.clicked = state;
		}
	}
}
