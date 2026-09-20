package com.huziyang520.onlytpviolentbuild.mixin;

import com.huziyang520.onlytpviolentbuild.CommandGuard;
import com.huziyang520.onlytpviolentbuild.Constants;
import com.huziyang520.onlytpviolentbuild.util.ModMsg;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayer {
    // 1.16.5(javap 实测)：setGameMode(GameType) 返回 void（1.18+ 才返回 boolean）
    @Inject(method = "setGameMode(Lnet/minecraft/world/GameType;)V",
            at = @At("HEAD"), cancellable = true)
    private void onSetGameMode(GameType gameType, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (!CommandGuard.shouldBlock(player, "gamemode")) {
            return;
        }
        Constants.LOG.info("Blocked gamemode change for {}", player.getName().getString());
        player.sendMessage(ModMsg.blocked(player), player.getUUID());
        ci.cancel();
    }
}
