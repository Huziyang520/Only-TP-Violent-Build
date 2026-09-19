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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Commands.class)
public class MixinCommands {
    @Inject(method = "performCommand(Lcom/mojang/brigadier/ParseResults;Ljava/lang/String;)V", at = @At("HEAD"), cancellable = true)
    private void onPerformCommand(ParseResults<CommandSourceStack> parsed, String command, CallbackInfo ci) {
        CommandSourceStack source = parsed.getContext().getSource();
        if (!(source.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        String root = rootOf(parsed);
        if (root == null || !CommandGuard.shouldBlock(player, root)) {
            return;
        }
        Constants.LOG.info("Blocked command \"{}\" from {}", root, player.getName().getString());
        player.sendSystemMessage(ModMsg.blocked(player));
        ci.cancel();
    }

    private String rootOf(ParseResults<CommandSourceStack> parsed) {
        List<ParsedCommandNode<CommandSourceStack>> nodes = parsed.getContext().getNodes();
        return nodes.isEmpty() ? null : nodes.get(0).getNode().getName();
    }
}
