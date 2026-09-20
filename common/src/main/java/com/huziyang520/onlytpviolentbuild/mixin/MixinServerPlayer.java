package com.huziyang520.onlytpviolentbuild.mixin;

import com.huziyang520.onlytpviolentbuild.CommandGuard;
import com.huziyang520.onlytpviolentbuild.Constants;
import com.huziyang520.onlytpviolentbuild.util.ModMsg;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer {
    @Inject(method = "setGameMode(Lnet/minecraft/world/level/GameType;)Z", at = @At("HEAD"), cancellable = true)
    private void onSetGameMode(GameType gameType, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        if (!CommandGuard.shouldBlock(player, "gamemode")) {
            return;
        }
        Constants.LOG.info("Blocked gamemode change for {}", player.getName().getString());
        player.sendSystemMessage(ModMsg.blocked(player));
        cir.setReturnValue(false);
        cir.cancel();
    }
}