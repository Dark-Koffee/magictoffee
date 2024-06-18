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
import net.minecraft.util.UseAction;

public class WandAttackHandler {

    public static void register() {
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
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (isWandInHand(player, hand)) {
                // Handles casting spells with the wand
                player.sendMessage(Text.literal("You right clicked with the wand"), true);
                return TypedActionResult.success(player.getStackInHand(hand));
            }
            return TypedActionResult.pass(player.getStackInHand(hand));
        });
    }

    private static boolean isWandInHand(PlayerEntity player, Hand hand) {
        return player.getStackInHand(hand).getItem() instanceof WandItem;
    }
}
