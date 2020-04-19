# ShadowKickboxing (WIP + not functioning yet)

## Introduction

App for shadow kickboxing training and to practice the Spring framework. This project is still in early development.


## Acknowledgements

#### Fight

* By design, defensive moves are not part of the implementation. User will be called to defend against offensive moves instead.
* During rest, the remaining time will be announced. A bell with sound at the end of each round.


#### Data / SQL

* @GeneratedValue(strategy=GenerationType.AUTO) is not used for the majority of entities. For the most part, the database will be seeded manually. Only a small part will be dynamic.

## Temporary notes

#### Unanswered development questions

* How to deal with stance (orthodox vs southpaw in combination with stance agnostic moves like the liver shot)?
    * Trainee decides for himself.
    * Per fight instruction about stance.
    * Periodic instruction about stance.