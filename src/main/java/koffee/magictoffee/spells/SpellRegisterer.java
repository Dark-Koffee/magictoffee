package koffee.magictoffee.spells;

import java.util.ArrayList;
import java.util.List;

public class SpellRegisterer {
    public static List<Spell> spells = new ArrayList<>();

    public Spell register(Spell spell) {
        spells.add(spell);
        return spell;
    }

    public static String[] spellList() {
        String[] spellNames = new String[spells.size()];
        for (int i = 0; i < spells.size(); i++) {
            spellNames[i] = spells.get(i).getID();
        }
        return spellNames;
    }

    public void registerSpellBook(Spell spell) {

    }
}

