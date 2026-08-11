package com.gonduzhang.lootbeamsapothfix;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

/**
 * LootBeams Apotheosis Fix
 * <p>
 * 独立修复模组:解决 LootBeams 与新版 Placebo/Apotheosis 的兼容崩溃。
 * 核心修复逻辑在 mixin/LootBeamsCompatMixin(HEAD 注入 + 短路返回)。
 * 本类仅作为 mod 入口,不注册任何内容。
 */
@Mod(LootBeamsApothFixMod.MODID)
public class LootBeamsApothFixMod
{
    public static final String MODID = "lootbeamsapothfix";
    private static final Logger LOGGER = LogUtils.getLogger();

    public LootBeamsApothFixMod()
    {
        LOGGER.info("LootBeams Apotheosis Fix loaded");
    }
}
