package koffee.magictoffee.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MagicComponentImpl implements MagicComponent, AutoSyncedComponent {
    private final Entity entity;
    private int selected = 0;
    private final String[] spells = new String[5];
    { // Default value of all empty slots
        Arrays.fill(spells, "magictoffee:empty");
    }
    private final Map<String, Long> spellCooldowns = new HashMap<>();

    private int mana = 100;
    private int manaCap = 100;

    public MagicComponentImpl(Entity entity) {
        this.entity = entity;
    }

    @Override
    public int getSelected() {
        return selected;
    }

    @Override
    public void setSelected(int selected) {
        this.selected = selected;
        sync();
    }

    @Override
    public String getSpell(int index) {
        if (index >= 0 && index < spells.length) {
            return spells[index];
        }
        return "magictoffee:empty";
    }

    @Override
    public void setSpell(int index, String spell) {
        if (index >= 0 && index < spells.length) {
            spells[index] = spell;
            sync();
        }
    }

    @Override
    public long getCooldown(String spellID) {
        return spellCooldowns.getOrDefault(spellID, 0L);
    }

    @Override
    public void setCooldown(String spellID, long cooldown) {
        spellCooldowns.put(spellID, cooldown);
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public void setMana(int mana) {
        this.mana = mana;
    }

    @Override
    public int getManaCap() {
        return manaCap;
    }

    @Override
    public void setManaCap(int manaCap) {
        this.manaCap = manaCap;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        // Selected Spell
        this.selected = tag.getInt("selected");
        // Spells
        for (int i = 0; i < spells.length; i++) {
            spells[i] = tag.getString("spell" + i);
        }
        // Spell Cooldowns
        spellCooldowns.clear();
        NbtCompound cooldownTag = tag.getCompound("spellCooldowns");
        for (String key : cooldownTag.getKeys()) {
            spellCooldowns.put(key, cooldownTag.getLong(key));
        }
        // Mana
        this.mana = tag.getInt("mana");
        // ManaCap
        this.mana = tag.getInt("manaCap");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        // Selected Spell
        tag.putInt("selected", selected);
        // Spells
        for (int i = 0; i < spells.length; i++) {
            tag.putString("spell" + i, spells[i]);
        }
        // Spell Cooldowns
        NbtCompound cooldownTag = new NbtCompound();
        for (Map.Entry<String, Long> entry : spellCooldowns.entrySet()) {
            cooldownTag.putLong(entry.getKey(), entry.getValue());
        }
        tag.put("spellCooldowns", cooldownTag);
        // Mana
        tag.putInt("mana", mana);
        // ManaCap
        tag.putInt("manaCap", mana);
    }

    private void sync() {
        if (entity instanceof ServerPlayerEntity) {
            ModComponents.SPELLS_COMPONENT_KEY.sync(entity);
        }
    }

    // For packets to talk to client
    public void writeCooldownsToBuf(PacketByteBuf buf) {
        buf.writeInt(spellCooldowns.size()); // Write the size of the map
        for (Map.Entry<String, Long> entry : spellCooldowns.entrySet()) {
            buf.writeString(entry.getKey()); // Write the spell ID
            buf.writeLong(entry.getValue()); // Write the cooldown
        }
    }
}