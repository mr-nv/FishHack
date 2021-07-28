package fuck.you.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import fuck.you.FishHack;
import fuck.you.event.events.EventMovementTick;
import fuck.you.event.events.EventTick;
import net.minecraft.client.network.ClientPlayerEntity;

@Mixin(ClientPlayerEntity.class)
public class MixinPlayerEntity {
	@Inject(at = @At("RETURN"), method = "tick()V", cancellable = true)
	public void tick(CallbackInfo callback)
	{
		EventTick event = new EventTick();
		FishHack.eventBus.post(event);
		if (event.isCancelled()) callback.cancel();
	}
	
	@Inject(at = @At("HEAD"), method = "sendMovementPackets()V", cancellable = true)
	public void sendMovementPackets(CallbackInfo info) {
		EventMovementTick event = new EventMovementTick();
		FishHack.eventBus.post(new EventMovementTick());
		if (event.isCancelled()) info.cancel();
	}
}
