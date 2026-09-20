package com.huziyang520.onlytpviolentbuild;

import com.huziyang520.onlytpviolentbuild.util.ModMsg;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 1.12.2 没有 Mixin，改用 Forge 自带的 CommandEvent 做命令拦截。
 *
 * <p>覆盖范围：玩家通过聊天栏/命令方块等发起的命令（Forge 在 CommandHandler 里派发该事件）。
 * <b>差异</b>：别的模组绕过命令系统直接调 {@code EntityPlayerMP#setGameType} 时拦不住
 * （1.16.5+ 分支用 Mixin 覆盖了这条路径）。
 */
public final class CommandBlocker {
    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        if (!(event.getSender() instanceof EntityPlayerMP)) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) event.getSender();
        String root = event.getCommand() == null ? null : event.getCommand().getName();
        if (root == null || !CommandGuard.shouldBlock(player, root)) {
            return;
        }
        Constants.LOG.info("Blocked command \"{}\" from {}", root, player.getName());
        event.setCanceled(true);
        ITextComponent msg = ModMsg.blocked();
        player.sendMessage(msg);
    }
}
