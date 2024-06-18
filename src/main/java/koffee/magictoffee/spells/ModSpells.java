package koffee.magictoffee.spells;

import koffee.magictoffee.MagicToffee;
import net.minecraft.block.Block;

public class ModSpells {

    // TestSpell
    public static final Spell testSpell = registerSpell(new TestSpell());
    // SecondSpell
    public static final Spell secondSpell = registerSpell(new SecondSpell());

    private static Spell registerSpell(Spell spell) {
        return new SpellRegisterer().register(spell);
    }

    public static void registerModSpells() {
        MagicToffee.LOGGER.info("Registering Mod Spells For Magic Toffee");
    }
}
