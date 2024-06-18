package koffee.magictoffee.spells;

import java.util.ArrayList;
import java.util.List;

public class SpellRegisterer {
    public static List<Spell> spells = new ArrayList<>();

    public Spell register(Spell spell) {
        spells.add(spell);
        return spell;
    }

}

