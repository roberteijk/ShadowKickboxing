# ShadowKickboxing

## Introduction

App for shadow kickboxing training and to practice the Spring framework.


## Acknowledgements

* AUTO_INCREMENT is not used in relational database (MySQL for now). This is partly because of foreign keys, partly because of unknown future implementations and changes.
* By design defensive moves are not part of the implementation. User will be called to defend against offensive moves instead.
* During rest, the remaining time will be announced.


## Temporary notes

#### Unanswered development questions

* How to deal with stance (orthodox vs southpaw in combination with stance agnostic moves like the liver shot)?
    * Trainee decides for himself.
    * Per fight instruction about stance.
    * Periodic instruction about stance.
    

#### SQL WIP in pseudo
    
* Combo (can be a single move to combination of moves):
    * id (primary key)
    * version (highest denominator of moves incorporated)
    * rule set (foreign key)
    * name (unique)
    * timings multiplier (as applied maximum)
    * collection of moves (unique)
    
    
* Fights (a store for pre-rendered fights for storage over CPU):
    * id (primary key)
    * version (highest denominator of combos incorporated)
    * rule set (foreign key)
    * rounds
    * collection of combos (unique)
    * audio fragment (unique)
    * distribution counter
    * date last distribution (may be coupled to IP, decide later)
    * IP list distributed
    
    
* Audio
    * id (primary key)
    * move (foreign key)
    * language (foreign key)
    * audio fragment
    
 
 * Language
    * id (primary key)
    * language (unique)


* Rule set
    * id (primary key)
    * name (unique)