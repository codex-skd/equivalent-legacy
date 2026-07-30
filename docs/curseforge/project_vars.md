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
| Upload | *pendiente* | Subir archivos JAR |
| Core (GET) | *pendiente* | Consultar datos del mod |

Autenticación Upload: cabecera `X-Api-Token`
Autenticación Core: cabecera `x-api-key`

> Token de cuenta (mismo para todos los mods) — copiar desde `codex-docs/secrets.md` o desde el `project_vars.md` de otro mod (ej. `skd_menu`).

## Variables para script (lectura automática)

project_id = 1632317
api_token = (pendiente — mismo token de cuenta usado en el resto de mods, ver `codex-docs/secrets.md`)
release_type = release
game_versions = (pendiente — obtener con GET a la API de CurseForge para MC 26.2 / NeoForge)

## Nota

La **primera subida a CurseForge se hace manual** (proyecto recién creado, sin archivos previos que verificar por API). A partir de la segunda subida se puede usar el script `codex-docs/scripts/curseforge-upload.ps1`.

## Rama

```
minecraft/26.2/neoforge-26.2.0.32-beta/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`
Ejemplo: `26.2-neoforge-0.0.0-beta.1`
