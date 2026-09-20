package com.huziyang520.onlytpviolentbuild.client;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

/** 进服时给客户端玩家一条提示（可用配置关闭）；1.7.10 用「本地玩家进入世界」事件代替登录事件。 */
@SideOnly(Side.CLIENT)
public final class ClientNotify {
    private boolean notified;

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event) {
        if (notified || !event.world.isRemote) {
            return;
        }
        if (!(event.entity instanceof EntityPlayer)) {
            return;
        }
        if (event.entity != Minecraft.getMinecraft().thePlayer) {
            return;
        }
        if (!Services.PLATFORM.isNotificationEnabled()) {
            return;
        }
        notified = true;
        ((EntityPlayer) event.entity).addChatMessage(
                new ChatComponentTranslation("onlytpviolentbuild.notification"));
    }
}
