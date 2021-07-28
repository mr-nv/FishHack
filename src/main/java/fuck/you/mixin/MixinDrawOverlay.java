package fuck.you.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fuck.you.FishHack;
import fuck.you.event.events.EventDrawOverlay;

@Mixin(InGameHud.class)
public class MixinDrawOverlay {
	
	@Inject(at = {@At(value = "INVOKE",
			target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V",
			ordinal = 4)}, method = {"render(F)V"})
	public void render(float partialTicks, CallbackInfo info)
	{
		EventDrawOverlay event = new EventDrawOverlay( partialTicks );
		FishHack.eventBus.post( event );
		if( event.isCancelled( ) ) info.cancel( );
	}
}