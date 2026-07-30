# CurseForge — Variables del proyecto

## Proyecto

| Variable | Valor |
|----------|-------|
| `curseforge_project_id` | *pendiente — crear proyecto en CurseForge* |
| `mod_id` | `equivalent_legacy` |
| `display_name` | `Equivalent Legacy` |

## Tokens

| API | Token | Uso |
|-----|-------|-----|
| Upload | *pendiente* | Subir archivos JAR |
| Core (GET) | *pendiente* | Consultar datos del mod |

Autenticación Upload: cabecera `X-Api-Token`
Autenticación Core: cabecera `x-api-key`

## Variables para script (lectura automática)

project_id = (pendiente)
api_token = (pendiente — mismo token de cuenta usado en el resto de mods, ver `codex-docs/secrets.md`)
release_type = release
game_versions = (pendiente)

## Rama

```
minecraft/26.2/neoforge-26.2.0.32-beta/production
```

## Tag

Formato: `<mc-version>-<framework>-<version>`
Ejemplo: `26.2-neoforge-0.0.0-beta.1`
