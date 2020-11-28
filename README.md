# dammit
A Java 8 and JavaFX based monster survival game, with a tower defense aspect thrown into the mix.

This project was primarily a collaboration with Elliot Van Huijgevoort, with additional help from
Gokul Nair. Collaboration was facilitated via git and gitlab. Unfortunately, I have been unable
to locate the original repository with the complete commit history, so the best I can do is to
emphasize that I am not responsible for the entirety of this repository.

DAMMIT is a top-down monster survival game. The game starts with the player on an empty field, and
monsters begin to swarm him at an ever increasing rate. Monsters are of three types - scorpions,
zombies, and giant crabs - which range from high speed and low health to low speed and high health.
The player is equipped with a gun with unlimited ammo which can be used to defend himself.
Additionally, powerups spawn randomly on the field at regular intervals. One powerup provides the
player with 20 giant super-bullets, which inflict extra damage. The other provides the player
with a Super Mario Bros star-like ability, with invincibility, heightened speed, and instant death
to whoever he touches. Moreover, money can be accrued by defeating monsters, and the money can then be
used to purchases towers. There are three types of towers. One is a turret, which can aim at a target
from across the map and continues to shoot until they die. The second is a water tower, which inflicts
area damage on nearby monsters. The third is a radio tower, which redirects all monster's targets to
itself as opposed to the player. Just like the player, these towers have a health bar, and monsters
can target and deal damage to the towers. A score is earned based on the number of monsters that are
defeated.

The game engine loops through each "actor" in the game, giving them a chance to "act." In our game, 
there were two main types of actors; those whose positions remained fixed on the screen, such as 
menu items and the player, and those whose positions moved when the player moved, such as the background. 
The way in which the top-down aspect of game was implemented was by putting all actors in the latter 
group in a pane that moved in the opposite direction from that in which the player "moved." Collision 
handling was dealt with by having a system of penetrable and impenetrable objects, where the player
and the monsters generally can not intersect impenetrable objects, with some exceptions. Inheritance
and polymorphism were used to build an architecture conducive to easy addition of features, and to keep
the code organized.

My personal contribution to the project involved developing basic player movement, creating the system of
impenetrable and penetrable objects, writing the collision handling code (including developing custom
bounds that maintained their size when an Actor rotates), developing the health, inventory, powerups, 
and strategic buildings, and developing most of the basic monster code.

My collaborators coded player rotation controls, added the scorpion and giant crab monsters in addition 
to the basic zombie, added enhanced visual and audio effects for powerups, and played a valuable role
in discussing ideas and solutions to problems.

If you happen to have Java 8 and JavaFX 8 set up on your machine, you might be able to download the jar
file and play the game yourself.
