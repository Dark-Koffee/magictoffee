package koffee.magictoffee.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.network.PacketByteBuf;

public interface MagicComponent extends ComponentV3 {
    int getSelected();
    void setSelected(int selected);

    String getSpell(int index);
    void setSpell(int index, String spell);

    long getCooldown(String spellID);
    void setCooldown(String spellID, long cooldown);

    int getMana();
    void setMana(int mana);

    int getManaCap();
    void setManaCap(int manaCap);

    void writeCooldownsToBuf(PacketByteBuf buf);
}