
# DiscordPlugin
[![Build](https://github.com/Omega-Network/DiscordPlugin/actions/workflows/build.yml/badge.svg)](https://github.com/Omega-Network/DiscordPlugin/actions/workflows/build.yml)
[![Support](https://img.shields.io/discord/805380967447134209?style=plastic)](https://discord.gg/omegahub)
[![License](https://img.shields.io/github/license/Omega-Network/DiscordPlugin)](about:blank)
[![Release](https://img.shields.io/github/v/release/Omega-Network/DiscordPlugin)](about:blank)
[![Downloads](https://img.shields.io/github/downloads/Omega-Network/DiscordPlugin/total)](about:blank)

### This plugin is in active development and is not recommended for production usage (it has many hardcoded variables).
## Omega Hub's implementation of the "abandoned" J-Vds Discord plugin for mindustry.

### Features
*138+ compatible*

### Setup
Make a bot and request a token. Make sure to give the bot some permissions.

#### Startup
When you run the plugin for the first time a config file will be made `config/disc/config.json`. In this file you can set some parameters to enable/disable some commands. 
You will also need to make a [discord bot](https://discord.com/developers/applications) and request a token.

I recommend to enable `Developer Mode` on discord. This is the easiest way to get the `id` values.

### Disable commands
You can disable some commands by removing or leaving some fields blank `""` in the config file.

#### Discord
* `..chat <text...>` Send a message to the in-game chat
* `..players` a list of the online players
* `..info` gives some info (amount of enemies, number of waves, name of the map)
* `..infores` amount of resources collected
* `..gameover` ends a game 
* `..maps` *custom* maps on the server

#### In-game
* `/d <text...>` Send a message to discord.
* `/gr [player] [reason...]` Alert admins on discord if someone is griefing (5 minute cool down)
* `/js [code...]` Send a random dude trying to execute js on your server to 2R2T (/js sandbox)

### Administrative Commands
#### Discord
* `..kick <player>` kick a player
* `..ban <player>` ban a player
* `..unban <player>` unban a player
* `..banlist` list of banned players
* `..admin <player>` give a player admin
* `..unadmin <player>` remove admin from a player
* `..admins` list of admins
* `..modchat <text...>` send a message as a administrator
* `..modteamchat <team ID> <text...>` send a message to a team as administrator
### Most of this commands are still in development and may not work as intended.

#### In-game
* `/ban <#ID>` ban a player

### Building the Jar

`gradlew jar` / `./gradlew jar`

Output jar should be in `build/libs`.
