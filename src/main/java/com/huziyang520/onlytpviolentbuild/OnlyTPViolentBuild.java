package com.huziyang520.onlytpviolentbuild;

import com.huziyang520.onlytpviolentbuild.platform.Services;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(
        modid = Constants.MOD_ID,
        name = "Only TP Violent Build",
        version = Constants.VERSION,
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
