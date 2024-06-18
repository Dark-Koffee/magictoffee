package koffee.magictoffee.item.custom;

import koffee.magictoffee.spells.SpellRegisterer;
import koffee.magictoffee.spells.Spell;
import koffee.magictoffee.spells.TestSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WandItem extends Item {
    public WandItem(Settings settings) { super(settings); }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    // Gets all the fancy little spells :)
    public SpellRegisterer registerSpells = new SpellRegisterer();
}
