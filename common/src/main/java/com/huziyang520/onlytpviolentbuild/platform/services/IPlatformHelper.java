package com.huziyang520.onlytpviolentbuild.platform.services;

import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public interface IPlatformHelper {
    boolean isModEnabled();

    boolean isExempt(ServerPlayer player);

    List<String> getCommandWhitelist();

    boolean isNotificationEnabled();
}