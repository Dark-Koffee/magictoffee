package koffee.magictoffee.spells;

import net.minecraft.entity.player.PlayerEntity;

public abstract class Spell {
    // -- Spell Class Fields --
    protected String spellID; // e.g. magictoffee:magicmissile
    protected String spellType; // e.g. generic
    protected String displayName; // e.g. Magic Missile
    protected long cooldown; // in ticks e.g. 20
    protected int manaCost; // in mana e.g. 10

    // -- Spell Class Constructors --

    // Default Constructor
    public Spell() {
        this.spellID = "magictoffee:none";
        this.spellType = "Basic";
        this.displayName = "Unknown";
        this.cooldown = 20;
        this.manaCost = 5;
    }

    // Overloaded Constructor
    public Spell(String id, String type, String display, int cooldown, int cost) {
        this.spellID = id;
        this.spellType = type;
        this.displayName = display;
        this.cooldown = cooldown;
        this.manaCost = cost;
    }

    // -- Spell Class Functions --

    // ActionOnUse has to be overloaded
    // What the spell does on use
    public abstract boolean ActionOnUse(PlayerEntity player);

    // Getters and Setters for Spell
    public String getID() {
        return this.spellID;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getSpellType() {
        return this.spellType;
    }

    public long getCooldown() {
        return this.cooldown;
    }

    public int getManaCost() {
        return this.manaCost;
    }

}