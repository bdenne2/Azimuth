Azimuth 0.2 - Minecraft Compass Plugin
=======================================

This [Bukkit](http://bukkit.org/) plugin allows players holding compasses to change where the compass points. Right clicking with a compass in hand will change the mode and send a message to the player.

Available modes:
----------------
	World spawn -- Points to the current world's spawn point
	Personal spawn -- Points to where player last spawned. Does not currently change if player sleeps in bed
	Last death -- Points to last location of death. This is set after the user dies.

Players can now add and remove modes from their rotation. The following commands are available:

Available commands:
-------------------
        /azimuth -- Lists available commands
        /compassAdd "world spawn" -- Adds world spawn back to rotation
        /compassRemove "personal spawn" -- Removes personal spawn from rotation


Changelog:
----------

### 0.2
  * Players can add and remove nodes from rotation

### 0.1
  * Three modes created
  * Multiple players with individual settings supported
  * Basic features implemented
