package koffee.magictoffee.spells;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.item.custom.SpellBook;
import koffee.magictoffee.util.DrawTools;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class HealSpell extends Spell{

    public HealSpell() {
        super.spellID = "magictoffee:heal";
        super.displayName = "Heal";
        super.bookName = this.displayName;
        super.manaCost = 20;
        super.spellBook = SpellBook.setSpell(new ItemStack(ModItems.spellbook), this.spellID);
    }

    @Override
    public boolean ActionOnUse(PlayerEntity player) {

        // Play fwip sound for the player
        player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), SoundCategory.AMBIENT, 1.0F, 2.0F);

        // Draw a ring of hearts around the player
        Vec3d loc = player.getPos();
        DrawTools.drawCircle((ServerWorld) player.getWorld(), new Vec3d(loc.getX(), loc.getY()+1, loc.getZ()), 1F, 0F, 0F, 24, MagicToffee.HEART, 1, 1);

        // Heal and feed the player
        player.heal(4);
        player.getHungerManager().add(4, 0.5F);

        return true;
    }
}
