package com.huziyang520.onlytpviolentbuild.platform;

import com.huziyang520.onlytpviolentbuild.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public final class Services {
    public static final IPlatformHelper PLATFORM = load();

    private static IPlatformHelper load() {
        // Java 8 的 ServiceLoader 没有 findFirst()/stream()
        for (IPlatformHelper helper : ServiceLoader.load(IPlatformHelper.class)) {
            return helper;
        }
        throw new NullPointerException("No platform helper found");
    }

    private Services() {
    }
}
