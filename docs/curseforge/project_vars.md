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

## Variables para script (lectura automática)

project_id = 1632317
api_token = ee776b0a-ee95-4850-b554-06be02a8657f
release_type = beta
game_versions = 9638,9639,16498,10150
relations = curios-api-updated:requiredDependency

## Dependencia requerida

| Mod | Proyecto CurseForge | Archivo | Versión |
|-----|---------------------|---------|---------|
| Curios API | `1579340` | `8270756` | `15.0.0-beta.2+26.2` |

## Nota

La **primera subida a CurseForge se hace manual** (proyecto recién creado, sin archivos previos que verificar por API). A partir de la segunda subida se puede usar el script `codex-docs/scripts/curseforge-upload.ps1`.

## Rama

```
minecraft/26.2/neoforge-26.2.0.32-beta/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`
Ejemplo: `26.2-neoforge-0.0.0-beta.1`

## Nota post-subida (manual, obligatorio)

El API de subida de CurseForge no expone el campo de lado (client/server). Tras **cada** subida de archivo hay que entrar en la web de CurseForge → pestaña "Files" → editar el archivo → marcar el entorno como **Client & Server** (este mod es BOTH: block entities, comandos, red EMC, Data Attachments en servidor). Sin este paso, el file queda etiquetado como solo cliente.

**Historial:**
- v1.2.0-RELEASE: ✅ Subido (File ID: 8591472), Phase 1 Complete - Containers + EMC Registry
- v1.2.0-beta.10: ✅ Subido (File ID: 8589845), Asset Namespace Migration Fix
- v1.2.0-beta.4: ✅ Subido (File ID: 8587006), Phase 2 World Transmutation
- v1.2.0-beta.3: ✅ Subido (File ID: 8585660), Assets completos
- v1.1.1: ✅ Marcado como Client & Server en CurseForge
