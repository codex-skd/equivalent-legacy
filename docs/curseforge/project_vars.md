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
minecraft/26.2/neoforge-26.2.0.45-beta/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`
Ejemplo: `26.2-neoforge-0.0.0-beta.1`

## Nota post-subida (manual, obligatorio)

El API de subida de CurseForge no expone el campo de lado (client/server). Tras **cada** subida de archivo hay que entrar en la web de CurseForge → pestaña "Files" → editar el archivo → marcar el entorno como **Client & Server** (este mod es BOTH: block entities, comandos, red EMC, Data Attachments en servidor). Sin este paso, el file queda etiquetado como solo cliente.

**Historial:**
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
