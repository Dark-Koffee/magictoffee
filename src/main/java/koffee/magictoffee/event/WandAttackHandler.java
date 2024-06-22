package koffee.magictoffee.event;
import koffee.magictoffee.components.MagicComponent;
import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.spells.Spell;
import koffee.magictoffee.util.SpellData;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import koffee.magictoffee.item.custom.WandItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.TypedActionResult;

public class WandAttackHandler {

    public static void register() {
        // When the player right clicks with a wand
        // Handles casting spells with the wand
        UseItemCallback.EVENT.register((player, world, hand) -> {
            // Running on server
            if (!world.isClient) {
                if (isWandInHand(player, hand)) {
                    // Get the player's selected spell
                    Spell spell = SpellData.getSpellFromID(SpellData.getSpell(player, SpellData.getSelected(player)));
                    String spellID = spell.getID();

                    // Gets the player's magic component & spell cooldown, and current time
                    MagicComponent magicComponent = ModComponents.SPELLS_COMPONENT_KEY.get(player);
                    long currentTime = player.getWorld().getTime();
                    long lastUsed = magicComponent.getCooldown(spellID);

                    // Gets the spell's cooldown
                    long spellCooldown = spell.getCooldown();

                    if (currentTime >= lastUsed+spellCooldown) {
                        // Cooldown is run before the action in case for some reason
                        // you wanted to override the cooldown in your spell (e.g. in case of a failed spell)
                        magicComponent.setCooldown(spellID, currentTime);
                        // Run the player's selected spell's action
                        if (spell.ActionOnUse(player)) {
                            // Only return success if the action returns true
                            return TypedActionResult.success(player.getStackInHand(hand));
                        }
                    } else {
                        player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 0.5F, 1.0F);
                        player.sendMessage(Text.literal("§cCooldown: " + String.format("%.2f", (lastUsed + spellCooldown - currentTime)/20.0f) + " Sec"), true);
                    }
                }
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
