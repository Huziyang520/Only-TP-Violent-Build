package com.huziyang520.onlytpviolentbuild.client;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.network.chat.Component;

public final class OnlyTPViolentBuildClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (Services.PLATFORM.isNotificationEnabled() && client.player != null) {
                // 1.21.6~1.21.8 的 LocalPlayer 没有 sendSystemMessage（那是服务端侧 API），
                // 客户端等价写法是 displayClientMessage(Component, boolean overlaid)。
                client.player.displayClientMessage(Component.translatable("onlytpviolentbuild.notification"), false);
            }
        });
    }
}
