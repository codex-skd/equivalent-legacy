# Equivalent Legacy (1.21.1) — Changelog

Branch `minecraft/1.21.1/neoforge-21.1.249/production`. History independent of the 26.2 branch.

## [1.1.4] - 2026-10-03

### Fixed

- **El Códice del Alquimista se veía igual que los demás libros del pack.** El guía es un único item
  (`vellumli:guide_book`) que se identifica por un componente de datos, y su `book.json` no declaraba
  modelo propio, así que Vellumli le ponía siempre la textura genérica `vellumli:book_brown`: el
  Códice salía con la misma portada que la Guía Workhand y que El Almanaque, y su EMC en el tooltip
  era el del item compartido. Ahora el libro declara su propio modelo y tiene portada propia
  (tapa de granate con el símbolo alquímico de transmutación en oro y cantoneras doradas), distinguible
  de un vistazo de las otras dos guías y de las variantes genéricas de Vellumli.

### Added

- `assets/equivalent_legacy/textures/item/guide.png` y `models/item/guide.json` — portada del Códice
  del Alquimista. Sin item nuevo, sin registro nuevo y sin código Java.

## [1.1.3] - 2026-10-02

### Fixed

- **Reloj del Tiempo Fluyente: el ciclo día/noche se aceleraba gratis y para siempre** con un solo
  clic derecho en el modo `Adelantamiento rápido` o `Rebobinado`. El bloque que empujaba el reloj
  del mundo estaba antes de la comprobación de activación (`ACTIVE`) y del coste de EMC, así que
  corría en cada tick (5x / 9x / 13x según la carga) aunque el jugador nunca activase el ítem y sin
  consumir energía. Ahora el empujón del reloj se ejecuta después de comprobar `ACTIVE` y de cobrar
  el EMC por tick, igual que el resto de efectos del anillo.
- **Reloj del Tiempo Fluyente: `Rebobinado` podía dejar el reloj del mundo clavado en 0**, con la
  fase de sol/luna fijada y sin ninguna forma de recuperarse dentro del juego. El límite inferior
  del rebobinado es ahora un día completo (24000 ticks), así que el reloj nunca queda en 0 ni
  desincroniza la fase del día.
- **Licencia**: `LICENSE` conserva ahora el aviso de copyright original de ProjectE/Equivox (`Copyright (c) 2020 Sin Tachikawa`), como exige la licencia MIT, además del de Stalking Dragons.
- **README**: el enlace a Regalia Slots API apunta ahora al repositorio público de GitHub (`github.com/codex-skd/regalia-slots-api`) en lugar del GitLab privado.

### Added

- **Wiki**: el generador de la guía (`guide/tools/build_guide.py`) publica la tabla de versiones de todas las ramas (1.21.1 y las cuatro de 26.2) con sus diferencias, y añade páginas solo de wiki en `guide/wiki/`: comandos y nodos de permiso, y créditos y licencia (inglés y español).

## [1.1.2] - 2026-09-30

### Fixed

- **Botas de Gema: el planeo no evitaba el daño de caída** con `balance.gemArmor.noFall = false`
  (valor por defecto). El planeo es solo de cliente y el servidor acumulaba toda la altura. Ahora,
  mientras el descenso que informa el cliente (`ServerPlayer#getKnownMovement`) no supera la
  velocidad de planeo (0,75 bloques/tick), la distancia de caída se limita a 3 bloques (como
  Caída Lenta); una caída rápida (picado con las grebas) sigue acumulando.
- **Grebas de Gema: el daño del picado no se aplicaba nunca**: se calculaba en el servidor con
  `getDeltaMovement()`, que no refleja el movimiento real del jugador. Usa `getKnownMovement()`.
- **Casco de Gema quitaba cualquier Visión Nocturna** (pociones, faros, otros mods) cuando su
  visión nocturna estaba desactivada. Ahora solo retira la suya (ambiental, invisible, ≤ 11 s).
- Tooltips del Casco y el Peto de Gema indican cuándo sus habilidades ofensivas están
  desactivadas por el servidor (`difficulty.offensiveAbilities`, desactivado por defecto).

## [1.1.1] - 2026-09-30

### Fixed

- **Crash del servidor al guardar (`Saving entity NBT` / `Value must be positive: 0`)** cuando un
  jugador llevaba un ítem con `stored_emc`, `stored_exp` o `unprocessed_emc` a `0` (p. ej. la
  Pechera de Gemas). Los helpers `registerNonNegative*` de `DataComponentTypeDeferredRegister`
  validaban `> 0`; ahora usan `ExtraCodecs.NON_NEGATIVE_INT` y `>= 0`.

## [1.1.0] - 2026-09-29

### Added

- **Survival balance system**: new `[balance]` section in `server.toml`. Every option falls back
  to the classic ProjectE value when `balance.enabled = false`. The balanced preset ships as the
  default: Repair Talisman costs 4 EMC per durability point; Body/Soul/Life stones heal/feed half
  a point every 10 s for 256 EMC; both only work from a curio slot; Gem Boots no longer cancel
  fall damage (+50% speed); PE armor full-set reduction x0.7; vein mining capped at 64 blocks and
  AOE radius halved; no armor-piercing hits; SWRG repel radius 3 at 1 EMC/tick; collectors
  generate 1/4 and only from sunlight; condensers cost x2; Mercurial Eye max 32 blocks per
  operation; catalysts 32 EMC per block; lens radius max 8; Red/Dark Matter furnace ore doubling
  0%/25%; dimension blacklist and effect EMC multiplier.
- **Alchemist's Codex**: in-game guide book (Vellumli), given once on first login and craftable
  from a book and Low Covalence Dust. Full English and Spanish content, also published on the
  GitHub wiki.
- **Vellumli is now a required dependency.**

### Changed

- Balanced defaults for existing options: Katar death aura 12, covalence loss 0.5, Watch of
  Flowing Time disabled, player repair/heal/feed cooldowns 200 ticks, projectile cooldown 20
  ticks, pedestal cooldowns doubled, Klein Stars must be full for recipes. Existing config files
  keep their stored values.
- Tooltips now describe hidden abilities (curio slots, EMC costs, water/lava walking, fire
  immunity, mode/charge keys) in English and Spanish.

### Fixed

- Mind Stone and Watch of Flowing Time did nothing in their curio slots.
- Swiftwolf's Rending Gale tooltip claimed it grants flight.

## [1.0.3] - 2026-09-15

### Fixed

- **Dark Matter, Red Matter and Gem armor could not be enchanted**: `PEArmor` hard-overrode
  `isEnchantable`, `isBookEnchantable`, `isPrimaryItemFor` and `supportsEnchantment` to always
  return `false`, unlike the 26.2 branch's equivalent class, which has no such overrides. On top
  of that, all three `ArmorMaterial` definitions (`dark_matter`, `red_matter`, `gem_armor`) had
  `enchantmentValue` set to `0`. Removed the blocking overrides from `PEArmor` and set
  `enchantmentValue` to 18/22/24 respectively, matching the values already used on the 26.2
  branch. The `enchantable/*` item tags were already correct on this branch, so no datapack
  changes were needed. `./gradlew clean build` OK; not yet verified in-game.

## [1.0.2] - 2026-09-13

### Fixed

- **Self-crafting-remainder items consumed on craft**: `ItemPE` had no override for
  NeoForge's stack-aware `getCraftingRemainingItem(ItemStack)` /
  `hasCraftingRemainingItem(ItemStack)`, unlike the 26.2 branch's equivalent override. Any item
  implementing `ISelfCraftingRemainder` — Philosopher's Stone, Repair Talisman, Volcanite Amulet,
  Evertide Amulet, Arcana Ring, Zero Ring — was silently consumed by any recipe using it as an
  ingredient instead of being returned to the crafting grid. Restored: for these items,
  `getCraftingRemainingItem` now returns a count-1 copy of the same stack (preserving its
  components, e.g. stored EMC) instead of falling through to vanilla's item-level remainder.
  `./gradlew clean build` OK; not yet verified in-game.

## [1.0.1] - 2026-09-13

### Fixed

- **Double-rendered alchemical chests**: `AlchemicalChest#getRenderShape` returned
  `RenderShape.MODEL`, so the chunk renderer baked and drew the static `base_chest.json` model
  (a closed chest with real geometry) in addition to `ChestRenderer` (the block-entity renderer)
  drawing its own animated lid/bottom/lock model. Alchemical Chest, Condenser and Condenser MK2
  looked like two superimposed chests — one static-closed, one animated — especially from a
  distance. Switched to `RenderShape.ENTITYBLOCK_ANIMATED`, matching vanilla `ChestBlock`: only
  the animated model is drawn now. No gameplay change. `./gradlew clean build` OK; not yet
  verified in-game.

## [1.0.0] - 2026-09-09

First stable release for **Minecraft 1.21.1 / NeoForge 21.1.249** (Java 21). Consolidates the
`0.0.0-beta.1` → `0.0.0-beta.4` line with no further code changes. This build has been running in
the *(Develop) Mystical Realms* modded-server pack.

### Summary of the beta line

- **beta.1** — initial API port of the stable 26.2 line (1.6.4), 474 classes; the fork's own
  adaptations preserved, only 26.2-only Minecraft/NeoForge API reverted to its 1.21.1 form (using
  upstream ProjectE 1.21.1 as the API reference). Dependency: Regalia Slots API (the team's fork
  of the Curios API). Fixed item loss when breaking machines (`Block#onRemove` +
  `dropContentsOnRemoval(...)` on the 7 block-entity classes) and re-encoded 161 recipe JSONs +
  2 tags from the 26.2 bare-string datapack format to the 1.21.1 object form (151 recipes and
  2 tags had failed to load).
- **beta.2** — JEI optional-dependency range corrected from `[30.15.0,)` (a newer-Minecraft
  template bound) to `[19.50.0.414,)`, the JEI `19.x` line used by 1.21.1.
- **beta.3** — added the 19 missing block-item models (`models/item/<id>.json`, each parenting its
  block model) so alchemical-fuel blocks, Collector MK1–3, Entropy Sink, Stellar Condenser, matter
  furnaces/blocks, Nova Catalyst/Cataclysm and Relay MK1–3 no longer render as the missing-texture
  checkerboard.
- **beta.4** — `pe_custom_conversions` files now honour NeoForge load conditions:
  `CustomConversionMapper` parses through `ConditionalOps` /
  `createConditionalCodecWithConditions`; the bundled ATM/Powah EMC compat was split into
  `atm_compat.json` (gated on `allthemodium`) and `powah_compat.json` (gated on `powah`),
  silencing the "Unable to deserialize key" ERROR spam on packs without those mods. EMC values
  unchanged.

### Notes

- No gameplay change relative to `0.0.0-beta.4`. Verified: `./gradlew clean build` is green;
  `./gradlew runServer` reaches `Done` with 0 FATAL and 0 recipe/tag parse errors.
- Same CurseForge project as the 26.2 line (`1632317`); pick the file that matches your Minecraft
  version. Requires Regalia Slots API.

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
