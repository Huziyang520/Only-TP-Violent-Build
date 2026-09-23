package com.huziyang520.onlytpviolentbuild;

import com.huziyang520.onlytpviolentbuild.client.OnlyTPViolentBuildClient;
import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public final class OnlyTPViolentBuild {
    public OnlyTPViolentBuild() {
        Constants.LOG.info("Only TP Violent Build initialized (enabled={})", Services.PLATFORM.isModEnabled());
        if (FMLLoader.getDist().isClient()) {
            MinecraftForge.EVENT_BUS.register(OnlyTPViolentBuildClient.class);
        }
    }
}