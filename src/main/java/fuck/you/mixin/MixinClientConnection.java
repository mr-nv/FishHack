package fuck.you.mixin;

import fuck.you.FishHack;
import fuck.you.event.events.EventReadPacket;
import fuck.you.event.events.EventSendPacket;
import fuck.you.event.events.EventChatMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {
	@Shadow
	private Channel channel;
	
	@Shadow
	private void sendImmediately(Packet<?> packet_1, GenericFutureListener<? extends Future<? super Void>> genericFutureListener_1) {}
	 
	@Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
	public void channelRead0(ChannelHandlerContext channelHandlerContext_1, Packet<?> packet_1, CallbackInfo callback) {
		if (this.channel.isOpen() && packet_1 != null) {
        	try {
                EventReadPacket event = new EventReadPacket(packet_1);
                FishHack.eventBus.post(event);
                if (event.isCancelled()) callback.cancel();
            } catch (Exception exception) {}
        }
	}
	
	@Inject(method = "send(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At("HEAD"), cancellable = true)
    public void send(Packet<?> packet_1, GenericFutureListener<? extends Future<? super Void>> genericFutureListener_1, CallbackInfo callback) {
    	if( packet_1 instanceof ChatMessageC2SPacket )
    	{
    		EventChatMessage chatevent = new EventChatMessage( ( ChatMessageC2SPacket )packet_1 );
    		FishHack.eventBus.post( chatevent );
    		if( chatevent.isCancelled( ) )
    		{
    			callback.cancel( );
    			return;
    		}
    	}
		
		EventSendPacket event = new EventSendPacket(packet_1);
    	FishHack.eventBus.post(event);
        if (event.isCancelled()) callback.cancel();
    }
}
