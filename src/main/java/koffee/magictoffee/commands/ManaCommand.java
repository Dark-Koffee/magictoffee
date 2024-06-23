package koffee.magictoffee.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import koffee.magictoffee.components.MagicComponent;
import koffee.magictoffee.components.ModComponents;
import koffee.magictoffee.networking.packet.Spell_ListS2CPacket;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ManaCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(
                literal("mana")
                        .requires(source -> source.hasPermissionLevel(2))
                        .then(
                                literal("get")
                                        .then(
                                                argument("player", EntityArgumentType.player())
                                                        .executes(ctx -> getMana(ctx, EntityArgumentType.getPlayer(ctx, "player")))
                                        )
                        )
                        .then(
                                literal("set")
                                        .then(
                                                argument("player", EntityArgumentType.player())
                                                        .then(
                                                                argument("amount", IntegerArgumentType.integer(0))
                                                                        .executes(ctx -> setMana(ctx, EntityArgumentType.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "amount")))
                                                        )
                                        )
                        )
        );
    }

    private static int getMana(CommandContext<ServerCommandSource> context, PlayerEntity player) {
        MagicComponent magicComponent = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        player.sendMessage(Text.literal("§9" + player.getEntityName() + " has " + magicComponent.getMana() + " mana"));
        return 1;
    }

    private static int setMana(CommandContext<ServerCommandSource> context, PlayerEntity player, int amount) {
        MagicComponent magicComponent = ModComponents.SPELLS_COMPONENT_KEY.get(player);
        magicComponent.setMana(amount); // Set player's mana
        Spell_ListS2CPacket.send(((ServerPlayerEntity) player)); // Send packet
        player.sendMessage(Text.literal("§9Set " + player.getEntityName() + "'s mana to " + amount));
        return 1;
    }
}