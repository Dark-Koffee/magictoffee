package koffee.magictoffee.item.custom;

import koffee.magictoffee.MagicToffee;
import koffee.magictoffee.networking.ModMessages;
import koffee.magictoffee.spells.SpellRegisterer;
import koffee.magictoffee.spells.Spell;
import koffee.magictoffee.spells.TestSpell;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WandItem extends Item {
    public WandItem(Settings settings) { super(settings); }

    // Rather than handling the use in here, we're doing it in WandAttackHandler because
    // for some reason this stopped working? Probably when I added WandAttackHandler to stop
    // the player from being able to left-click with the wand.
    public TypedActionResult<ItemStack> use(World world, ServerPlayerEntity player, Hand hand) {
        player.sendMessage(Text.literal("You right clicked with the wand"), true);
        return TypedActionResult.success(player.getStackInHand(hand));
    }

    // Gets all the fancy little spells :)
    public SpellRegisterer registerSpells = new SpellRegisterer();
}
