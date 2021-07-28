package fuck.you.mixin;

import fuck.you.FishHack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockItem.class)
public class MixinPlaceBlock {
	 @Redirect(method = "getPlacementState", at = @At(
	            value = "INVOKE",
	            target = "Lnet/minecraft/block/Block;getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;"
	    ))
	 private BlockState getPlacementState(Block block, ItemPlacementContext itemPlacementContext)
	 {
		 return FishHack.helperBlockDirection.handleDirection( block, itemPlacementContext );
	 }
}
