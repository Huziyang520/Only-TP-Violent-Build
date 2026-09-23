package com.huziyang520.onlytpviolentbuild.client;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class OnlyTPViolentBuildClient {
    @SubscribeEvent
    public static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        if (Services.PLATFORM.isNotificationEnabled() && event.getPlayer() != null) {
            // 1.21.2+ 的 LocalPlayer 没有 sendSystemMessage（那是服务端侧 API），
            // 客户端等价写法是 displayClientMessage(Component, boolean overlaid)。
            event.getPlayer().displayClientMessage(Component.translatable("onlytpviolentbuild.notification"), false);
        }
    }

    private OnlyTPViolentBuildClient() {
    }
}