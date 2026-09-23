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
            // 1.21.6+ 的 LocalPlayer 没有 sendSystemMessage（那是服务端侧 API），
            // 客户端等价写法是 displayClientMessage(Component, boolean overlaid)。
            event.getPlayer().displayClientMessage(Component.translatable("onlytpviolentbuild.notification"), false);
        }
    }

    private OnlyTPViolentBuildClient() {
    }
}