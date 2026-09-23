package com.huziyang520.onlytpviolentbuild.platform;

import com.huziyang520.onlytpviolentbuild.ModConfig;
import com.huziyang520.onlytpviolentbuild.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public final class FabricPlatformHelper implements IPlatformHelper {
    private final ModConfig config;

    public FabricPlatformHelper() {
        this.config = new ModConfig(FabricLoader.getInstance().getConfigDir());
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
