# LootBeams Apotheosis Fix

**Mod ID:** `lootbeamsapothfix` · **作者:** gonduzhang · **MC 1.20.1 / Forge 47.x**

---

## 这个模组修什么?(大白话)

**LootBeams(战利品光束)与 神化(Apotheosis)一起使用时,掉落神化品质装备会直接崩溃。**

### 具体是哪个版本、哪个方法、什么场景

| 项目 | 详情 |
|---|---|
| 崩溃模组 | LootBeams **1.20.1-1.2.2** |
| 冲突对象 | Placebo **8.6.3** / Apotheosis **7.4.8** |
| 崩溃方法 | `DynamicHolder.get()`(Placebo 库的方法) |
| 崩溃类型 | `NoSuchMethodError`(找不到方法) |
| 触发场景 | 掉落/生成**神化品质物品**时,ItemEntity 每 tick 刷新光束颜色的那一刻 |

### 为什么会崩

LootBeams 1.2.2 按**旧版 Placebo** 编译,字节码里调用 `DynamicHolder.get()` 用的是旧签名;
Placebo 8.6.3 把该方法的签名改了 → **钥匙对不上锁**,一调用就 `NoSuchMethodError`。
而 LootBeams 恰恰是在掉落物每 tick 刷新光束时调用它,所以**神化品质物品一掉出来必崩**。

### 怎么处理的

- 向 `com.lootbeams.compat.ApotheosisCompat` 注入补丁(Mixin),在 `getRarityName` / `getRarityColor`
  两个方法**入口处拦截**;
- 神化品质物品:**用当前新版 Placebo 的 API 直接算出品质名/颜色并短路返回**,根本不会走到
  后面那个会崩溃的旧调用;
- 普通物品:放行,走 LootBeams 原逻辑(原逻辑对它们安全);
- 未安装 LootBeams 时,补丁自动跳过(`@Pseudo`),不影响游戏。

### 效果

神化品质装备正常显示光束颜色,不再崩溃。

---

## 安装要求

- Minecraft **1.20.1** + Forge
- 需要已安装(建议版本,其它相近版本也可能兼容):
  - LootBeams **1.20.1-1.2.2**(本模组的修复目标)
  - Apotheosis **7.4.8**
  - Placebo **8.6.3**

> 三个依赖均为可选(optional):缺失时本模组不报错,但只有三者齐全才有修复效果。

---

## 下载 / 构建

发布版 jar 在 CurseForge 页面下载。
自行构建:

```bash
./gradlew build
# 产物: build/libs/lootbeamsapothfix-1.0.0.jar
```

## 许可

All Rights Reserved.
