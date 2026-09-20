package com.huziyang520.onlytpviolentbuild.platform.services;

import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.List;

public interface IPlatformHelper {
    boolean isModEnabled();

    boolean isExempt(ServerPlayerEntity player);

    List<String> getCommandWhitelist();

    boolean isNotificationEnabled();
}
