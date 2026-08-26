package com.huziyang520.onlytpviolentbuild;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.fabricmc.api.ModInitializer;

public final class OnlyTPViolentBuild implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOG.info("Only TP Violent Build initialized (enabled={})", Services.PLATFORM.isModEnabled());
    }
}