package com.huziyang520.onlytpviolentbuild.mixin;

import com.huziyang520.onlytpviolentbuild.CommandGuard;
import com.huziyang520.onlytpviolentbuild.Constants;
import com.huziyang520.onlytpviolentbuild.util.ModMsg;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.ParsedCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Commands.class)
public class MixinCommands {
    // 1.18.2 Commands.performCommand takes (CommandSourceStack, String) -> int (the ParseResults overload only exists in 1.19+)
    @Inject(method = "performCommand(Lnet/minecraft/commands/CommandSourceStack;Ljava/lang/String;)I", at = @At("HEAD"), cancellable = true, remap = true)
    private void onPerformCommand(CommandSourceStack source, String command, CallbackInfoReturnable<Integer> cir) {
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        String root = rootOf(command);
        if (root == null || !CommandGuard.shouldBlock(player, root)) {
            return;
        }
        Constants.LOG.info("Blocked command \"{}\" from {}", root, player.getName().getString());
        // 1.18.2 的发送签名是 sendMessage(Component, UUID)
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