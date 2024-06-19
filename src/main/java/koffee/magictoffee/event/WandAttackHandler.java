package koffee.magictoffee.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import koffee.magictoffee.item.custom.WandItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.TypedActionResult;

public class WandAttackHandler {

    public static void register() {
        // When the player right clicks with a wand
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (isWandInHand(player, hand)) {
                // Handles casting spells with the wand
                player.sendMessage(Text.literal("Cast spell"), true);
                return TypedActionResult.success(player.getStackInHand(hand));
            }
            return TypedActionResult.pass(player.getStackInHand(hand));
        });
        // Cancel Hitting a block
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (isWandInHand(player, hand)) {
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });

        // Cancel Hitting an entity
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (isWandInHand(player, hand)) {
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
    }

    // Checks if the player has the wand in their hand.
    public static boolean isWandInHand(PlayerEntity player, Hand hand) {
        return player.getStackInHand(hand).getItem() instanceof WandItem;
    }
}
