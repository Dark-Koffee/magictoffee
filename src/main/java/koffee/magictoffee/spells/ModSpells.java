package koffee.magictoffee.spells;
import koffee.magictoffee.MagicToffee;

public class ModSpells {

    // TestSpell
    public static final Spell testSpell = registerSpell(new TestSpell());
    // Empty Spell
    public static final Spell emptySpell = registerSpell(new EmptySpell());
    // SecondSpell
    public static final Spell magicMissile = registerSpell(new MagicMissile());

    private static Spell registerSpell(Spell spell) {
        return new SpellRegisterer().register(spell);
    }

    public static void registerModSpells() {
        MagicToffee.LOGGER.info("Registering Mod Spells For Magic Toffee");
    }
}
