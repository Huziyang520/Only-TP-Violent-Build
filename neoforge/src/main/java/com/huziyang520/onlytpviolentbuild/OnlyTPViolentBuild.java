package com.huziyang520.onlytpviolentbuild;

import com.huziyang520.onlytpviolentbuild.client.OnlyTPViolentBuildClient;
import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Constants.MOD_ID)
public final class OnlyTPViolentBuild {
    public OnlyTPViolentBuild(IEventBus bus, Dist dist) {
        Constants.LOG.info("Only TP Violent Build initialized (enabled={})", Services.PLATFORM.isModEnabled());
        if (dist.isClient()) {
            NeoForge.EVENT_BUS.register(OnlyTPViolentBuildClient.class);
        }
    }
}
