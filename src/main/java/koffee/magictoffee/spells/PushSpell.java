package koffee.magictoffee.spells;

import koffee.magictoffee.components.MagicComponent;
import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.item.ModItems;
import koffee.magictoffee.item.custom.SpellBook;
import koffee.magictoffee.util.KoffeeSpellTools;
import koffee.magictoffee.util.DrawTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class PushSpell extends Spell{

    public PushSpell() {
        super.spellID = "magictoffee:push";
        super.displayName = "Push";
        super.bookName = this.displayName;
        super.cooldown = 10;
        super.spellBook = SpellBook.setSpell(new ItemStack(ModItems.spellbook), this.spellID);
    }

    @Override
    public boolean ActionOnUse(PlayerEntity player) {
        Vec3d targetBlock = KoffeeSpellTools.getTargetBlock(player, 10, true);
        KoffeeSpellTools.TargetEntityResult entityResult = KoffeeSpellTools.getTargetEntity(player, 10);
        Vec3d playerPos = player.getEyePos();

        if (entityResult != null) {
            // Get the entity the player is lookin' at
            Entity targetEntity = entityResult.getEntity();

            // Get vector between player and entity
            Vec3d vector = targetEntity.getPos().subtract(playerPos);

            // Get vector to push the entity with
            Vec3d desiredVector = (vector.normalize()).multiply(1.5D);

            // Push the entity away from ya
            targetEntity.addVelocity(desiredVector);

            // Play whoosh sound for player
            player.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.AMBIENT, 1.0F, 2.0F);

            // Get location to draw particles at
            Vec3d offset = vector.normalize().multiply(0.75);
            Vec3d particlePos = (entityResult.getHitPos()).add(offset);

            // Draw a circle at the block the player's looking at
            DrawTools.drawCircle((ServerWorld) player.getWorld(), particlePos, 0.75D, player.headYaw-90, player.getPitch()-90, 16, ParticleTypes.INSTANT_EFFECT, 0.0, 5);

            return true;
        }
        else if (targetBlock != null) {
            // Get vector between player and block
            Vec3d vector = playerPos.subtract(targetBlock);

            // Get vector to push player with
            Vec3d desiredVector = (vector.normalize()).multiply(1.5D);

            // Get location to draw particles at
            Vec3d offset = vector.normalize().multiply(0.75);
            Vec3d particlePos = targetBlock.add(offset);

            // Using addVelocity for additive effect
            player.addVelocity(desiredVector);

            // Play whoosh sound for the player
            player.playSound(SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.AMBIENT, 1.0F, 2.0F);

            // Draw the circle
            DrawTools.drawCircle((ServerWorld) player.getWorld(), particlePos, 0.75D, player.headYaw-90, player.getPitch()-90, 16, ParticleTypes.INSTANT_EFFECT, 0.0, 5);

            // Update on the client
            player.velocityModified = true;
            return true;
        } else {
            player.playSound(SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.AMBIENT, 1.0F, 1.0F);
            MagicComponent magicComponent = ModComponents.SPELLS_COMPONENT_KEY.get(player);
            magicComponent.setCooldown(spellID, 0);
            magicComponent.setMana(magicComponent.getMana()+manaCost);
            return false;
        }
    }
}
