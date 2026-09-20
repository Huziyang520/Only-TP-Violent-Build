package com.huziyang520.onlytpviolentbuild;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
        modid = Constants.MOD_ID,
        name = "Only TP Violent Build",
        version = Constants.VERSION,
        acceptedMinecraftVersions = "[1.12,1.13)",
        acceptableRemoteVersions = "*"
)
public class OnlyTPViolentBuild {
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CommandBlocker());
        Constants.LOG.info("Only TP Violent Build initialized (enabled={})", Services.PLATFORM.isModEnabled());
        if (FMLCommonHandler.instance().getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new com.huziyang520.onlytpviolentbuild.client.ClientNotify());
        }
    }
}
