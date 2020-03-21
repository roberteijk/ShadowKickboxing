# ShadowKickboxing

## Introduction

App for shadow kickboxing training and to practice the Spring framework.


## Acknowledgements

#### Fight

* By design, defensive moves are not part of the implementation. User will be called to defend against offensive moves instead.
* During rest, the remaining time will be announced. A bell with sound at the end of each round.


#### SQL

* AUTO_INCREMENT is not used in relational database except for the fight table. This is partly because of foreign keys, partly because of unknown future implementations and changes.


## Temporary notes

#### Unanswered development questions

* How to deal with stance (orthodox vs southpaw in combination with stance agnostic moves like the liver shot)?
    * Trainee decides for himself.
    * Per fight instruction about stance.
    * Periodic instruction about stance.