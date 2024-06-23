package koffee.magictoffee.spells;
import koffee.magictoffee.MagicToffee;

public class ModSpells {

    // Test Spell
    public static final Spell testSpell = registerSpell(new TestSpell());
    // Empty Spell
    public static final Spell emptySpell = registerSpell(new EmptySpell());
    // Magic Missile
    public static final Spell magicMissileSpell = registerSpell(new MagicMissile());
    // Push Spell
    public static final Spell pushSpell = registerSpell((new PushSpell()));
    // Pull Spell
    public static final Spell pullSpell = registerSpell(new PullSpell());
    // Heal Spell
    public static final Spell healSpell = registerSpell(new HealSpell());
    // Slow fall
    public static final Spell slowFallSpell = registerSpell(new SlowFallSpell());

    private static Spell registerSpell(Spell spell) {
        return new SpellRegisterer().register(spell);
    }

    public static void registerModSpells() {
        MagicToffee.LOGGER.info("Registering Mod Spells For Magic Toffee");
    }
}
