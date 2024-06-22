package koffee.magictoffee.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class MagicComponentImpl implements MagicComponent, AutoSyncedComponent {
    private final Entity entity;
    private int selected = 0;
    private final String[] spells = new String[5];
    {
        for (int i = 0; i < spells.length; i++) {
            spells[i] = "magictoffee:empty";
        }
    }

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
    public void readFromNbt(NbtCompound tag) {
        this.selected = tag.getInt("selected");
        for (int i = 0; i < spells.length; i++) {
            spells[i] = tag.getString("spell" + i);
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("selected", selected);
        for (int i = 0; i < spells.length; i++) {
            tag.putString("spell" + i, spells[i]);
        }
    }

    private void sync() {
        if (entity instanceof ServerPlayerEntity) {
            ModComponents.SPELLS_COMPONENT_KEY.sync(entity);
        }
    }
}