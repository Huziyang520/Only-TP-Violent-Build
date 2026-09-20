package com.huziyang520.onlytpviolentbuild.client;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 进服时给客户端玩家一条提示（可用配置关闭）。
 *
 * <p>1.12.2 没有 {@code ClientPlayerNetworkEvent}（1.13+ 才有），
 * 这里用「本地玩家进入世界」事件代替，并用 notified 标记保证一次会话只提示一次。
 */
@SideOnly(Side.CLIENT)
public final class ClientNotify {
    private boolean notified;

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event) {
        if (notified || !event.getWorld().isRemote) {
            return;
        }
        if (!(event.getEntity() instanceof EntityPlayer)) {
            return;
        }
        if (event.getEntity() != Minecraft.getMinecraft().player) {
            return;
        }
        if (!Services.PLATFORM.isNotificationEnabled()) {
            return;
        }
        notified = true;
        ((EntityPlayer) event.getEntity()).sendMessage(
                new TextComponentTranslation("onlytpviolentbuild.notification"));
    }
}
