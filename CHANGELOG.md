# Equivalent Legacy (1.21.1) — Changelog

Branch `minecraft/1.21.1/neoforge-21.1.249/production`. History independent of the 26.2 branch.

## [0.0.0-beta.4] - 2026-09-03

### Changed

- **pe_custom_conversions files now support neoforge load conditions.** The bundled
  ATM/Powah EMC compat (`atm_powah_compat.json`) has been split into `atm_compat.json`
  (gated behind `neoforge:mod_loaded` for `allthemodium`) and `powah_compat.json`
  (gated behind `neoforge:mod_loaded` for `powah`). The original file is kept empty for
  backwards compat. This silences "Unable to deserialize key" ERROR spam when those mods
  are absent. `CustomConversionMapper` now parses with the conditional codec, matching
  the pattern used by `WorldTransmutationManager`.

## [0.0.0-beta.3] - 2026-09-02

### Fixed

- **Missing item textures on 19 block items.** Minecraft 1.21.1 resolves item models from
  `assets/equivalent_legacy/models/item/<id>.json`. The port produced those models for every
  standalone item but not for the block items, so Alchemical Coal / Mobius Fuel / Aeternalis Fuel
  blocks, Collector MK1-3, Entropy Sink (all tiers), Stellar Condenser, Dark/Red Matter Furnace,
  Dark/Red Matter blocks, Nova Catalyst, Nova Cataclysm and Relay MK1-3 all rendered as the
  missing-texture checkerboard in the creative inventory and in hand. Added the 19 item models,
  each parenting its existing block model (same pattern already used by `dm_pedestal`,
  `interdiction_torch` and the chest-like blocks).

### Notes

- The `assets/equivalent_legacy/items/` directory (item model definitions) is the 1.21.4+ format
  and is ignored by 1.21.1; it is left in place but has no effect on this version.

## [0.0.0-beta.2] - 2026-09-02

### Fixed

- **JEI optional dependency range.** `neoforge.mods.toml` declared the JEI dependency as
  `versionRange="[30.15.0,)"`, a bound carried over from a newer-Minecraft template. JEI for
  1.21.1 uses the `19.x` line, so any real install failed the check and NeoForge logged
  `Unsupported installed optional dependencies: Mod ID: 'jei' ... Expected range: '[30.15.0,)'`.
  The range is now `[19.50.0.414,)` — the JEI build shipped by the target modpack, or newer.
  JEI stays `type="optional"`; the mod still loads and works without it.

## [0.0.0-beta.1] - 2026-09-02

### Added

- **Initial port to Minecraft 1.21.1 / NeoForge 21.1.249** (Java 21). API port of the stable
  26.2 line (1.6.4), 474 classes. The fork's own adaptations (behaviour, added/removed features,
  renames) are preserved; only 26.2-only Minecraft / NeoForge API was reverted to its 1.21.1 form,
  using upstream ProjectE 1.21.1 as the API reference. Dependency: Regalia Slots API (the team's
  own fork of the Curios API), not Curios.

### Fixed

- **Item loss when breaking machines** (regression introduced while removing the 26.2-only
  `BlockEntity#preRemoveSideEffects` hook). Breaking a Collector, Relay, Condenser, Matter Furnace,
  Pedestal, Entropy Sink or Alchemical Chest voided its stored items instead of dropping them.
  Each block entity now exposes `dropContentsOnRemoval(...)`, called from a `Block#onRemove`
  override on the seven block classes — `onRemove` still runs while the block entity is valid,
  unlike `onBlockStateChange`.
- **151 recipes and 2 tags failed to load.** The 26.2 datapack used bare-string ingredients
  (`"minecraft:flint_and_steel"`, `"#c:gems/emerald"`) where 1.21.1 requires the object form
  (`{"item": "..."}` / `{"tag": "..."}`). All 161 recipe JSON files under `data/equivalent_legacy/recipe/`
  re-encoded, preserving the fork's recipe set and the `neoforge:components` ingredients. Removed
  `minecraft:bush` from `tags/block/override/plantable` and 7 spawn eggs added after 1.21.1 from
  `tags/item/ignore_missing_emc`.

### Technical

- 26.2 → 1.21.1 API reversions: `net.minecraft.resources.Identifier` → `ResourceLocation`;
  the 26.2 unified `neoforge.transfer` API → 1.21.1 `neoforge.items` / `fluids` / `energy`
  capabilities; `Item.appendHoverText` reverted to the 4-arg `(ItemStack, Item.TooltipContext,
  List<Component>, TooltipFlag)` form (no `TooltipDisplay`, ~112 sites); `Item.use` →
  `InteractionResultHolder<ItemStack>`; `Item.hurtEnemy` / `inventoryTick` / `onCraftedBy`
  signatures; the 1.21.1 recipe / `RecipeSerializer` / `CustomRecipe` / `assemble` API; the
  custom `DeferredRegister` / `DeferredHolder` / `PEDeferredHolder` layer; `Util` package move;
  `EntitySpawnReason` → `MobSpawnType`; `EntityTypes` → `EntityType`;
  `net.minecraft.util.TriState` → `net.neoforged.neoforge.common.util.TriState`;
  `ItemUseAnimation` → `UseAnim`; `AddServerReloadListenersEvent` → `AddReloadListenerEvent`;
  `net.minecraft.world.clock` / `net.minecraft.server.permissions` removed;
  `FuelValues` → `ItemStack.getBurnTime`; `ScheduledTickAccess` → 1.21.1 `updateShape`;
  JEI `IRecipeType` → `RecipeType`; `ItemInput.createItemStack`; `KeyMapping.Category`;
  `ServerExplosion` → `Explosion`. 26.2-only method bodies were reverted rather than dropped;
  the only files removed are 4 26.2-only shims with no 1.21.1 form — the render-state classes
  `rendering/ChestRenderState` and `rendering/PedestalRenderState` (the 26.2 render-state
  architecture does not exist in 1.21.1) and `utils/LegacyItemHandlerResourceHandler` /
  `utils/LegacyFluidHandlerResourceHandler` (wrappers around the 26.2 unified transfer API).
- Build: `net.neoforged.moddev` retargeted to NeoForge 21.1.249 / Java 21; `modLoader` /
  `loaderVersion` added to `neoforge.mods.toml`; `crafttweaker` / `jade` / `top` / `emi`
  integration packages excluded from compilation (kept in source). `regalia_slots_api` is
  `compileOnly` against its `-api.jar`, with the full mod jar on `localRuntime` so the dedicated
  server loads it.
- Verified: `./gradlew build` OK; `./gradlew runServer` → `Done (6.8s)`, 0 FATAL, 0 recipe/tag
  parse errors, EMC mapper and world-transmutation files register with no exceptions.
  Not verified in-game or with a running client.
