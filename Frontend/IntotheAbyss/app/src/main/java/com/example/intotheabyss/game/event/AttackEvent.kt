package com.example.intotheabyss.game.event

/**
 * An Event subclass to handle attacks on the client-side.
 * @constructor Creates an AttackEvent object.
 * @param attackedID The Entity ID that attacked another Entity.
 * @param attackedID The Entity ID that was attacked.
 * @param damage The damage the attacked was dealt.
 *
 * @author Benjamin Vogel
 */
class AttackEvent(attackerID: String, attackedID: String, val damage: Int):
    Event(attackerID, attackedID, EventType.ATTACK)