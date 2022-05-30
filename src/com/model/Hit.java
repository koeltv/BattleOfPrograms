package com.model;

/**
 * This record is used to transmit an attack to a target soldier.<br>
 * It contains the damages that would be caused upon receiving the attack and the chance of it hitting as a multiplier (ex: 1.1).
 */
public record Hit(float attackValue, float hitChance) {
}
