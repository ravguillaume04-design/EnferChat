# EnferChat

Plugin Minecraft Spigot 1.20.4 gérant le format du chat, les messages de mort et les notifications de changement de niveau.

## Fonctionnalités

- Format de chat configurable `[Niveau] Pseudo : message` avec support hex (`&#RRGGBB`)
- Messages de mort personnalisés affichant le niveau du joueur
- Notifications de changement de niveau (level-up, reset mort, modifications admin)
- Intégration optionnelle (soft-depend) avec le plugin [Niveaux](https://github.com/ravguillaume04-design/Niveaux)

## Dépendances

| Dépendance | Type | Version |
|---|---|---|
| Spigot | requise | 1.20.4 |
| Niveaux | optionnelle | 1.0.0 |

## Compilation

1. Installer le plugin Niveaux dans le dépôt Maven local :
   ```bash
   cd ../Niveaux
   mvn install
   ```

2. Compiler EnferChat :
   ```bash
   cd ../EnferChat
   mvn package
   ```

Le jar final se trouve dans `target/enferchat-1.0.0.jar`.

## Configuration

Tous les formats sont éditables dans `config.yml`. Les codes couleur hex (`&#RRGGBB`) et les codes Bukkit (`&a`, `&c`, etc.) sont supportés partout.
