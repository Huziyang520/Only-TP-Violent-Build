package com.huziyang520.onlytpviolentbuild.client;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class OnlyTPViolentBuildClient {
    @SubscribeEvent
    public static void onLogin(ClientPlayerNetworkEvent.LoggedInEvent event) {
        if (Services.PLATFORM.isNotificationEnabled() && event.getPlayer() != null) {
            event.getPlayer().displayClientMessage(
                    new TranslationTextComponent("onlytpviolentbuild.notification"), false);
        }
    }

    private OnlyTPViolentBuildClient() {
    }
}
