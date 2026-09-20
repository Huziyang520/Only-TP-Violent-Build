package com.huziyang520.onlytpviolentbuild.platform;

import com.huziyang520.onlytpviolentbuild.ModConfig;
import com.huziyang520.onlytpviolentbuild.platform.services.IPlatformHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Loader;

import java.util.List;

public final class ForgePlatformHelper implements IPlatformHelper {
    private final ModConfig config = new ModConfig(Loader.instance().getConfigDir().toPath());

    @Override
    public boolean isModEnabled() {
        return config.isModEnabled();
    }

    @Override
    public boolean isExempt(EntityPlayerMP player) {
        return config.isExempt(player.getName());
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
