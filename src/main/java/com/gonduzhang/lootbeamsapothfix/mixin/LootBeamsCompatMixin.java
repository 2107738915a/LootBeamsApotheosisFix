package com.gonduzhang.lootbeamsapothfix.mixin;

import dev.shadowsoffire.apotheosis.adventure.affix.AffixHelper;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 修复 LootBeams 1.2.2 与新版 Placebo 不兼容导致的崩溃。
 *
 * <p>LootBeams 发布的 jar 按旧 Placebo 编译，字节码里调用
 * {@code DynamicHolder.get()Ldev/.../TypeKeyed;}，而新版 Placebo 8.6.3
 * 的签名已改为 {@code get()Ljava/lang/Object;} → NoSuchMethodError（丢神化
 * 品质物品时在 ItemEntity tick 触发）。</p>
 *
 * <p>对神化品质物品，用当前 Placebo API 直接取品质名/颜色并短路返回，
 * 绕过旧签名的崩溃调用；非神化物品走原方法（原方法对它们安全）。</p>
 *
 * <p>{@code @Pseudo} — LootBeams 缺失时静默跳过。</p>
 */
@Pseudo
@Mixin(targets = "com.lootbeams.compat.ApotheosisCompat", remap = false)
public class LootBeamsCompatMixin {

    @Inject(method = "getRarityName", at = @At("HEAD"), cancellable = true, remap = false)
    private static void wpcard$fixRarityName(ItemStack stack, CallbackInfoReturnable<String> cir) {
        var rarity = AffixHelper.getRarity(stack);
        if (rarity != null && rarity.isBound()) {
            cir.setReturnValue(rarity.get().toComponent().getString().toLowerCase());
        }
    }

    @Inject(method = "getRarityColor", at = @At("HEAD"), cancellable = true, remap = false)
    private static void wpcard$fixRarityColor(ItemStack stack, CallbackInfoReturnable<TextColor> cir) {
        var rarity = AffixHelper.getRarity(stack);
        if (rarity != null && rarity.isBound()) {
            cir.setReturnValue(rarity.get().getColor());
        }
    }
}
