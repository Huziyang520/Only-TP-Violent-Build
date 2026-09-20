package com.huziyang520.onlytpviolentbuild.mixin;

import com.huziyang520.onlytpviolentbuild.CommandGuard;
import com.huziyang520.onlytpviolentbuild.Constants;
import com.huziyang520.onlytpviolentbuild.util.ModMsg;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Commands.class)
public class MixinCommands {
    // 1.16.5(javap 实测)：performCommand(CommandSource, String) -> int
    @Inject(method = "performCommand(Lnet/minecraft/command/CommandSource;Ljava/lang/String;)I",
            at = @At("HEAD"), cancellable = true, remap = true)
    private void onPerformCommand(CommandSource source, String command, CallbackInfoReturnable<Integer> cir) {
        if (!(source.getEntity() instanceof ServerPlayerEntity)) {
            return;
        }
        ServerPlayerEntity player = (ServerPlayerEntity) source.getEntity();
        String root = rootOf(command);
        if (root == null || !CommandGuard.shouldBlock(player, root)) {
            return;
        }
        Constants.LOG.info("Blocked command \"{}\" from {}", root, player.getName().getString());
        player.sendMessage(ModMsg.blocked(player), player.getUUID());
        cir.setReturnValue(0);
    }

    private String rootOf(String command) {
        if (command == null) {
            return null;
        }
        int i = 0;
        while (i < command.length() && (command.charAt(i) == '/' || command.charAt(i) == ' ')) {
            i++;
        }
        if (i >= command.length()) {
            return null;
        }
        int start = i;
        while (i < command.length() && command.charAt(i) != ' ') {
            i++;
        }
        return command.substring(start, i);
    }
}
