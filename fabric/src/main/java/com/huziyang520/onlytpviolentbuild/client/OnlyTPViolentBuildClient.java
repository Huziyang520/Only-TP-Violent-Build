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
                client.player.sendSystemMessage(Component.translatable("onlytpviolentbuild.notification"));
            }
        });
    }
}