package com.huziyang520.onlytpviolentbuild.platform;

import com.huziyang520.onlytpviolentbuild.ModConfig;
import com.huziyang520.onlytpviolentbuild.platform.services.IPlatformHelper;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.loading.FMLPaths;

import java.util.List;

public final class NeoForgePlatformHelper implements IPlatformHelper {
    private final ModConfig config;

    public NeoForgePlatformHelper() {
        this.config = new ModConfig(FMLPaths.CONFIGDIR.get());
    }

    @Override
    public boolean isModEnabled() {
        return config.isModEnabled();
    }

    @Override
    public boolean isExempt(ServerPlayer player) {
        return config.isExempt(player.getName().getString());
    }

    @Override
    public List<String> getCommandWhitelist() {
        return config.getCommandWhitelist();
    }

    @Override
    public boolean isNotificationEnabled() {
        return config.isNotificationEnabled();
    }
}
