package com.huziyang520.onlytpviolentbuild.platform.services;

import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;

public interface IPlatformHelper {
    boolean isModEnabled();

    boolean isExempt(EntityPlayerMP player);

    List<String> getCommandWhitelist();

    boolean isNotificationEnabled();
}
