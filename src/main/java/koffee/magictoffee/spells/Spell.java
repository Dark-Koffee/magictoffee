package koffee.magictoffee.spells;

import net.minecraft.entity.player.PlayerEntity;

public abstract class Spell {
    // -- Spell Class Fields --
    protected String spellName;
    protected String spellType;
    protected int cooldown;
    protected int manaCost;

    // -- Spell Class Constructors --

    // Default Constructor
    public Spell() {
        this.spellName = "<none>";
        this.spellType = "<none>";
        this.manaCost = 0;
    }

    // Overloaded Constructor
    public Spell(String name, String type, int cooldown, int cost) {
        this.spellName = name;
        this.spellType = type;
        this.cooldown = cooldown;
        this.manaCost = cost;
    }

    // -- Spell Class Functions --

    // ActionOnUse has to be overloaded
    // What the spell does on use
    public abstract void ActionOnUse(PlayerEntity user);

    // Getters and Setters for Spell
    public String getName() {
        return this.spellName;
    }

    public String getSpellType() {
        return this.spellType;
    }

    public int getManaCost() {
        return this.manaCost;
    }

    public void setName(String name) {
        this.spellName = name;
    }

    public void setSpellType(String type) {
        this.spellType = type;
    }

    public void setManaCost(int cost) {
        this.manaCost = cost;
    }

}
