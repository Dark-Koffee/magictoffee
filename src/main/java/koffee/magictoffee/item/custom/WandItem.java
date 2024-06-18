package koffee.magictoffee.item.custom;

import koffee.magictoffee.spells.Spell;
import koffee.magictoffee.spells.TestSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WandItem extends Item {
    public WandItem(Settings settings) { super(settings); }

    public Spell testSpell = new TestSpell();


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        testSpell.ActionOnUse(user);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
