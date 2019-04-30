package com.example.intotheabyss.game.entity.monster

import com.example.intotheabyss.game.entity.Entity
import com.example.intotheabyss.game.entity.EntityType

/**
 * A Monster Class. Inherits from MonsterInterface and will be a subclass of Entity (still in development). Logic and
 * values are yet to be established as monster logic has not been completely finished on back-end.
 *
 * @author Benjamin Vogel
 */
class Monster : MonsterInterface, Entity {

    /**
     * A constructor to create a Monster from an Entity
     * @param e An Entity to cast to Monster.
     */
<<<<<<< HEAD
    constructor(e: Entity) : super(e.ID, e.x, e.y, e.floor, e.health, 0, 0, 0, EntityType.MONSTER)
=======
    constructor(e: Entity) : super(e.ID, e.x, e.y, 0, 0, e.floor, e.health, 0, EntityType.MONSTER)
>>>>>>> 452af72b70c836a92949e685a1a6167d4e843d8f

}
