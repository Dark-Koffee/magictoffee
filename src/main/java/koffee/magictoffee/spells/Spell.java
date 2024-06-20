package koffee.magictoffee.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public abstract class Spell {
    // -- Spell Class Fields --
    protected String spellID;
    protected String spellType;
    protected String displayName;
    protected int cooldown;
    protected int manaCost;

    // -- Spell Class Constructors --

    // Default Constructor
    public Spell() {
        this.spellID = "magictoffee:none";
        this.spellType = "Basic";
        this.displayName = "Unknown";
        this.cooldown = 0;
        this.manaCost = 0;
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
    public abstract void ActionOnUse(PlayerEntity player);

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

    public int getCooldown() {
        return this.cooldown;
    }

    public int getManaCost() {
        return this.manaCost;
    }

}