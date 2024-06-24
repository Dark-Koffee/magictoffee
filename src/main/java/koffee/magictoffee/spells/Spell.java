package koffee.magictoffee.spells;

import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.item.custom.SpellBook;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public abstract class Spell {
    // -- Spell Class Fields --
    protected String spellID; // e.g. magictoffee:magicmissile
    protected String spellType; // e.g. generic
    protected String displayName; // e.g. Magic Missile
    protected long cooldown; // in ticks e.g. 20
    protected int manaCost; // in mana e.g. 10
    protected boolean registerBook; // in mana e.g. 10
    protected String bookName; // in mana e.g. 10
    protected String description; // in mana e.g. 10
    protected ItemStack spellBook;

    // -- Spell Class Constructors --

    // Default Constructor
    public Spell() {
        this.spellID = "magictoffee:none";
        this.spellType = "Basic";
        this.displayName = "Unknown";
        this.bookName = "Unknown";
        this.cooldown = 20;
        this.manaCost = 5;
        this.registerBook = true;
        this.description = "This is a spellbook";
        this.spellBook = SpellBook.setSpell(new ItemStack(ModItems.spellbook), this.getID());

    }

    // Overloaded Constructor
    public Spell(String id, String type, String display, String bookName, int cooldown, int cost, boolean registerBook, String description, ItemStack spellBook) {
        this.spellID = id;
        this.spellType = type;
        this.displayName = display;
        this.bookName = bookName;
        this.cooldown = cooldown;
        this.manaCost = cost;
        this.registerBook = registerBook;
        this.description = description;
        this.spellBook = spellBook;
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

    public boolean getRegisterBook() {
        return this.registerBook;
    }

    public String getBookName() {
        return this.bookName;
    }

    public String getDescription() {
        return this.description;
    }

    public ItemStack getSpellBook() {return this.spellBook;}
}