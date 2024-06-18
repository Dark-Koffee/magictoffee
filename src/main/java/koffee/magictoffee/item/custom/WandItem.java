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
        //testSpell.ActionOnUse(user);{
        user.sendMessage(Text.literal("-- Spells --"), false);
        user.playSound(SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, 2.0F, 1.0F);
        for(int i = 0; i < SpellRegisterer.spells.size(); i++) {
            user.sendMessage(Text.of(SpellRegisterer.spells.get(i).getName()), false);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    // Gets all the fancy little spells :)
    public SpellRegisterer registerSpells = new SpellRegisterer();
}
