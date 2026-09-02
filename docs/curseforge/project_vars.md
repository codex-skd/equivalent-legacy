# CurseForge — Variables del proyecto

## Proyecto

| Variable | Valor |
|----------|-------|
| `curseforge_project_id` | `1632317` |
| `mod_id` | `equivalent_legacy` |
| `display_name` | `Equivalent Legacy` |

## Tokens

| API | Token | Uso |
|-----|-------|-----|
| Upload | `ee776b0a-ee95-4850-b554-06be02a8657f` | Subir archivos JAR |
| Core (GET) | `$2a$10$yGwryAfmRkS9ZJsJUDf5YOKZpOIsmHB8Fji2D8JVCKBSZEKYlwmaO` | Consultar datos del mod |

Autenticación Upload: cabecera `X-Api-Token`
Autenticación Core: cabecera `x-api-key`

> Token de cuenta (mismo para todos los mods, ver `data_miner/26.1.2/docs/curseforge/project_vars.md` u otros).

# Variables para script (lectura automática)

project_id = 1632317
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
release_type = release
game_versions = 9638,9639,16498,10150
relations =

## Dependencia requerida

| Mod | Proyecto CurseForge | Archivo | Versión |
|-----|---------------------|---------|---------|
| Regalia Slots API | N/A (nuestro fork mantenido) | N/A | 0.0.0-beta.2 |

## Nota

La **primera subida a CurseForge se hace manual** (proyecto recién creado, sin archivos previos que verificar por API). A partir de la segunda subida se puede usar el script `codex-docs/scripts/curseforge-upload.ps1`.

## Rama

```
minecraft/26.2/neoforge-26.2.0.57/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`
Ejemplo: `26.2-neoforge-0.0.0-beta.1`

## Nota post-subida (manual, obligatorio)

El API de subida de CurseForge no expone el campo de lado (client/server). Tras **cada** subida de archivo hay que entrar en la web de CurseForge → pestaña "Files" → editar el archivo → marcar el entorno como **Client & Server** (este mod es BOTH: block entities, comandos, red EMC, Data Attachments en servidor). Sin este paso, el file queda etiquetado como solo cliente.

**Historial:**
- v1.6.5: ✅ Subido como RELEASE (File ID: <PENDING>). Los ficheros `pe_custom_conversions` ahora respetan las condiciones de carga de NeoForge. El compat EMC de ATM/Powah empaquetado (`atm_powah_compat.json`) se cargaba siempre y, sin `allthemodium` ni `powah`, `CustomConversionMapper` escupía decenas de `Unable to deserialize key: … allthemodium:…` / `Failed to read conversions: … powah:…` a nivel ERROR en cada mapeo de EMC. Partido en `atm_compat.json` (gate `mod_loaded → allthemodium`) y `powah_compat.json` (`→ powah`), original vacío. `CustomConversionMapper` envuelve las ops en `ConditionalOps` + `createConditionalCodecWithConditions` (patrón de `WorldTransmutationManager`) y salta en silencio los ficheros sin condiciones cumplidas. Valores EMC intactos. Mismo cambio en 1.21.1 (`0.0.0-beta.4`, File ID 8795801). `clean build` OK en ambas ramas. Sin verificar in-game.
- v1.6.4: ✅ Subido como RELEASE (File ID: 8755196). El Talismán de Reparación seguía sin reparar items en ranuras de Regalia Slots API — 1.6.3 lo daba por corregido pero solo tocó tags `curios:*`, no `RepairTalisman.java`. El handler que recorre las ranuras es el puente legacy `IItemHandler.of(ResourceHandler)` de NeoForge (`ItemResourceHandlerAdapter`), no modificable y con `getStackInSlot` devolviendo copias, así que `repairAllItems` mutaba una copia muerta. Ahora, cuando el handler no es modificable, repara vía `extractItem`+`insertItem` con guarda `isItemValid`. Nota: fix compilado, pendiente de verificación en juego.
- v1.6.3: ✅ Subido como RELEASE (File ID: 8752907). Compatibilidad con Regalia Slots API: `gem_of_eternal_density` y `mind_stone` añadidos al tag `curios:necklace` (se podían no colocar en la ranura de amuleto), `gem_of_eternal_density` quitada de `curios:ring` por ser amuleto; Talismán de Reparación en ranura de cinturón repara también los ítems equipados en ranuras de Regalia (collar, anillo, cinturón…). Nota: el crash de arranque del pack "(Update) EnchantVenture" NO era de este mod — lo provocaba `regalia_slots_api` 1.1.1, cuyos 5 descriptores `META-INF/services/…ICurios*` seguían apuntando a las clases `Curios*Adapter` renombradas a `Regalia*Adapter`; corregido en `regalia_slots_api` 1.1.2.
- v1.6.2: ✅ Subido como RELEASE (File ID: 8728163). 17 ítems con efectos activos/pasivos (repulsión de mobs, curación/alimentación automática, congelar área, ignición/extinción de fuego, atracción de ítems, drenaje de XP, anulación de daño de caída, aceleración de bloques cercanos, etc.) no explicaban ese efecto en su tooltip — añadida una línea descriptiva a los 17 ítems, traducida a los 11 idiomas del mod.
- v1.6.1: ✅ Subido como RELEASE (File ID: 8697574). El Vendaval Desgarrador de Swiftwolf y el Anillo Arcano concedían vuelo creativo sin querer (por llevarlos encima, sin depender del modo/EMC en el caso del Arcana) — eliminada la concesión de vuelo de ambos ítems.
- v1.6.0: ✅ Subido como RELEASE (File ID: 8695874). Las 6 teclas del mod (efectos de yelmo/botas, cargar, función extra, disparar proyectil, cambiar modo) tienen ahora su propia categoría "Equivalent Legacy" en el menú de Controles, en vez de aparecer mezcladas bajo la categoría vanilla "Gameplay".
- v1.5.3: ✅ Subido como RELEASE (File ID: 8687979). Sharpness/Smite/Bane of Arthropods restaurados para dm_sword/rm_sword/dm_axe/rm_axe/rm_katar (excluidos de `enchantable/sharp_weapon` desde 1.5.0) + NeoForge actualizado de 26.2.0.45-beta a 26.2.0.57.
- v1.5.2: ✅ Subido como RELEASE (File ID: 8685596). Dependencia Curios sustituida por Regalia Slots API (fork propio del equipo, LGPL-3.0) + fix de la carpeta de configuración (`config/EquivalentLegacy/` → `config/equivalent_legacy/`, breaking para instalaciones existentes).
- v1.5.1: ✅ Subido como RELEASE (File ID: 8684377). Break-drop fix completed: Condenser (MK1/MK2), Entropy Sink (all tiers), Relay (MK1/MK2/MK3) and the Alchemical Chest still lost their inventory on break — 1.5.0 only covered Collector/Pedestal/MatterFurnace. Added the same `BlockEntity#preRemoveSideEffects` override to the remaining item-holding block entities.
- v1.5.0: ✅ Subido como RELEASE (File ID: 8683135). PE swords/tools/armor now enchantable (per-tier enchantment value + minecraft:enchantable/* tags), Collector/Pedestal/Matter Furnace break-drop fix (moved from the too-late `onBlockStateChange` to `BlockEntity#preRemoveSideEffects`, the 1.4.3 fix never actually worked).
- v1.4.3: ✅ Subido como RELEASE (File ID: 8673149). Collector MK1 stray sprite fix, item-loss-on-break fix, transmutation preview icon stuck over open GUIs, 3 overpriced EMC values fixed, ProjectE UUID checker removed, `PECore`→`ELCore` rename.
- v1.4.2: ⚠️ Subido por el script como BETA (File ID: 8641239) porque `release_type` seguía en `beta` desde la línea 1.3.0-beta.x — corregido manualmente a RELEASE en CurseForge por el usuario. `release_type` actualizado a `release` en este archivo para que las próximas subidas de la línea 1.4.x salgan correctas sin corrección manual. Server crash fix (corrupted block entity data in `WorldHelper#getBlockEntity`).
- v1.4.0: ✅ Subido como RELEASE (File ID: 8620093), Full re-port from Equivox 26.2 reference + EMC runtime fix (commons-math3 jarJar) + Spanish translation
- v1.3.0-beta.14: ✅ Subido (File ID: 8619937), Spanish translation & display name fix
- v1.3.0-beta.13: ✅ Subido (File ID: 8619825), Full re-port from Equivox 26.2 reference
- v1.3.0-beta.7: ✅ Subido (File ID: 8594175), Full Release Workflow - All Phases 1-6 Complete
- v1.3.0-beta.6: ✅ Subido (File ID: 8594148), Full Feature Set - All Phases 1-6 Complete (FOR TESTING)
- v1.3.0-beta.5: ✅ Subido (File ID: 8594106), Phase 6.1 - Alchemical Chest Network Complete
- v1.3.0-beta.4: ✅ Subido (File ID: 8593827), Phase 5.2 - Alchemical Chests Complete
- v1.3.0-beta.3: ✅ Subido (File ID: 8593729), Phase 5.1 - Matter Furnace Complete
- v1.3.0-beta.2: ✅ Subido (File ID: 8593400), Phase 4 - Mob Farming System Complete
- v1.3.0-beta.1: ✅ Subido (File ID: 8591843), Phase 3 - Transmutation GUI Improvements
- v1.2.1-RELEASE: 🗂️ ARCHIVED en CurseForge (File ID: 8591686)
- v1.2.0-RELEASE: 🗂️ ARCHIVED en CurseForge (File ID: 8591472)
- v1.2.0-beta.10: ✅ Subido (File ID: 8589845), Asset Namespace Migration Fix
- v1.2.0-beta.4: ✅ Subido (File ID: 8587006), Phase 2 World Transmutation
- v1.2.0-beta.3: ✅ Subido (File ID: 8585660), Assets completos
- v1.1.1: ✅ Marcado como Client & Server en CurseForge
