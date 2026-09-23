package com.huziyang520.onlytpviolentbuild.client;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;

public final class OnlyTPViolentBuildClient {
    // EventBus 7（Forge 1.21.9+ / 26.x）不再接受 @SubscribeEvent + register(Class)：
    // 单监听类会报 "Only a single listener found in class ..."，改为在事件自带的 BUS 上直接 addListener。
    public static void register() {
        ClientPlayerNetworkEvent.LoggingIn.BUS.addListener(OnlyTPViolentBuildClient::onLogin);
    }

    public static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        if (Services.PLATFORM.isNotificationEnabled() && event.getPlayer() != null) {
            // 26.x 的 LocalPlayer 用 sendSystemMessage(Component)（与同分支 fabric/neoforge 端一致）
            event.getPlayer().sendSystemMessage(Component.translatable("onlytpviolentbuild.notification"));
        }
    }

    private OnlyTPViolentBuildClient() {
    }
}