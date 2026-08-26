package com.huziyang520.onlytpviolentbuild.client;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class OnlyTPViolentBuildClient {
    @SubscribeEvent
    public static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        if (Services.PLATFORM.isNotificationEnabled() && event.getPlayer() != null) {
            event.getPlayer().sendSystemMessage(Component.translatable("onlytpviolentbuild.notification"));
        }
    }

    private OnlyTPViolentBuildClient() {
    }
}