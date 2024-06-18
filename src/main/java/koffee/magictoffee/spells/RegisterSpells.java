package koffee.magictoffee.spells;

import java.util.ArrayList;
import java.util.List;

public class RegisterSpells {
    private List<Spell> Spells = new ArrayList<>();

    public void register(Spell spell) {
        Spells.add(spell);
    }

}

