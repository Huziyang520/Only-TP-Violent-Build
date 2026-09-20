package com.huziyang520.onlytpviolentbuild.platform;

import com.huziyang520.onlytpviolentbuild.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public final class Services {
    public static final IPlatformHelper PLATFORM = load();

    private static IPlatformHelper load() {
        // 1.16.1 的 ModLauncher 只有 6.1.1，还不支持从模组 jar 里读 META-INF/services，
        // 默认的 ServiceLoader.load(clazz) 走 TCCL（系统类加载器）会一个都找不到，
        // 症状是 "ExceptionInInitializerError: No platform helper found"。
        // 1.16.5 的 ModLauncher 8.x 才修好这点 ⇒ 这里显式指定本类自己的 classloader。
        try {
            for (IPlatformHelper helper : ServiceLoader.load(IPlatformHelper.class,
                    IPlatformHelper.class.getClassLoader())) {
                return helper;
            }
        } catch (Throwable ignored) {
            // 落到下面的兜底
        }
        // 兜底：本工程是单加载器（只有 Forge），直接实例化一定可用。
        return new ForgePlatformHelper();
    }

    private Services() {
    }
}
