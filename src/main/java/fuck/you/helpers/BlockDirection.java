package fuck.you.helpers;

import com.google.common.eventbus.Subscribe;

import fuck.you.InfoLogger;
import fuck.you.event.events.EventChatMessage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.GlazedTerracottaBlock;
import net.minecraft.block.ObserverBlock;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.Direction;

public class BlockDirection {
	public String direction = "NONE";
	
	@Subscribe
	public void chatMessage( EventChatMessage event )
	{
		String chatmessage = event.getPacket( ).getChatMessage( ).toLowerCase( );
		if( !chatmessage.startsWith( "-direction" ) &&
			!chatmessage.startsWith( ">direction" ) ) return;
		
		event.setCancelled( true );
		
		chatmessage = chatmessage.substring( 1 );
		
		switch( chatmessage )
		{
		case "direction up":
			direction = "UP";
			InfoLogger.info( "Changed block direction to UP" );
			break;
		case "direction down":
			direction = "DOWN";
			InfoLogger.info( "Changed block direction to DOWN" );
			break;
		case "direction north":
			direction = "north";
			InfoLogger.info( "Changed block direction to NORTH" );
			break;
		case "direction east":
			direction = "EAST";
			InfoLogger.info( "Changed block direction to EAST" );
			break;
		case "direction west":
			direction = "WEST";
			InfoLogger.info( "Changed block direction to WEST" );
			break;
		case "direction south":
			direction = "SOUTH";
			InfoLogger.info( "Changed block direction to SOUTH" );
			break;
		case "direction none":
		default:
			direction = "NONE";
			InfoLogger.info( "Changed block direction to NONE. Available BlockDirection related commands: direction up/down/north/east/west/south/none" );
			break;
		}
	}
	
	public boolean isGoodBlock( Block block )
	{
		return ( block instanceof GlazedTerracottaBlock ||
				block instanceof ObserverBlock ||
				block instanceof RepeaterBlock ||
				block instanceof ComparatorBlock ||
				block instanceof DispenserBlock ||
				block instanceof PistonBlock ||
				block instanceof StairsBlock );
	}
	
	public BlockState handleDirection( Block block, ItemPlacementContext itemPlacementContext )
	{
		if( !isGoodBlock( block ) )
			return block.getPlacementState( itemPlacementContext );
		
		switch( direction )
		{
		case "UP":
			return block.getDefaultState( ).with( FacingBlock.FACING, Direction.UP );
		case "DOWN":
			return block.getDefaultState( ).with( FacingBlock.FACING, Direction.DOWN );
		case "NORTH":
			return block.getDefaultState( ).with( FacingBlock.FACING, Direction.NORTH );
		case "EAST":
			return block.getDefaultState( ).with( FacingBlock.FACING, Direction.EAST );
		case "WEST":
			return block.getDefaultState( ).with( FacingBlock.FACING, Direction.WEST );
		case "SOUTH":
			return block.getDefaultState( ).with( FacingBlock.FACING, Direction.SOUTH );
		case "NONE":
		default:
			return block.getPlacementState( itemPlacementContext );
		}
	}
}
