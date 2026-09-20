package com.huziyang520.onlytpviolentbuild;

import com.huziyang520.onlytpviolentbuild.util.ModMsg;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.CommandEvent;

/**
 * 1.7.10 没有 Mixin / 也没有 1.12 的事件（事件类同在 net.minecraftforge.event 下），
 * 这里用 Forge 自带的 CommandEvent 做命令拦截。
 */
public final class CommandBlocker {
    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        // 1.7.10 的 CommandEvent 暴露的是公有字段（sender / command / parameters），没有 getter
        if (!(event.sender instanceof EntityPlayerMP)) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) event.sender;
        String root = event.command == null ? null : event.command.getCommandName();
        if (root == null || !CommandGuard.shouldBlock(player, root)) {
            return;
        }
        Constants.LOG.info("Blocked command \"{}\" from {}", root, player.getCommandSenderName());
        event.setCanceled(true);
        player.addChatMessage(ModMsg.blocked());
    }
}
