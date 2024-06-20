package koffee.magictoffee.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import koffee.magictoffee.networking.packet.Spell_ListS2CPacket;
import koffee.magictoffee.spells.SpellRegisterer;
import koffee.magictoffee.util.IEntityDataSaver;
import koffee.magictoffee.util.SpellData;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import java.util.Arrays;
import java.util.List;

public class SpellsCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        // Suggestion provider for slots
        SuggestionProvider<ServerCommandSource> slotSuggestionProvider = (context, builder) -> {
            for (int i = 1; i <= 5; i++) {
                builder.suggest(String.valueOf(i));
            }
            return builder.buildFuture();
        };

        // Suggestion provider for spells
        SuggestionProvider<ServerCommandSource> spellSuggestionProvider = (context, builder) -> {
            String[] spellArray = SpellRegisterer.spellList();
            List<String> spellList = Arrays.asList(spellArray);
            return CommandSource.suggestMatching(spellList, builder);
        };

        LiteralArgumentBuilder<ServerCommandSource> command = CommandManager.literal("spells")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("set")
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .then(CommandManager.argument("slot", IntegerArgumentType.integer(1, 5))
                                        .suggests(slotSuggestionProvider)  // Suggestions for slot
                                        .then(CommandManager.argument("spell", StringArgumentType.greedyString())
                                                .suggests(spellSuggestionProvider)  // Suggestions for spell
                                                .executes(context -> executeSet(context))
                                        )
                                )
                        )
                )
                .then(CommandManager.literal("get")
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .executes(context -> executeGet(context))
                        )
                );

        dispatcher.register(command);
    }

    private static int executeSet(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
        ServerCommandSource executor = context.getSource();
        int slot = IntegerArgumentType.getInteger(context, "slot");
        String spell = StringArgumentType.getString(context, "spell");
        if (isValidSpell(spell)) {
            SpellData.setSpell((IEntityDataSaver) player, slot-1, spell);
            Spell_ListS2CPacket.send(player);
            executor.sendMessage(Text.literal("\u00A7dSpell slot " + slot + " for \u00A75" + player.getEntityName() + "\u00A7d set to \u00A76" + spell));
            return 1;
        } else {
            executor.sendMessage(Text.literal("\u00A7cInvalid Spell!"));
            return 0;
        }
    }

    private static int executeGet(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
        ServerCommandSource executor = context.getSource();
        executor.sendMessage(Text.literal("\u00A75" + player.getEntityName() + "'s Spells"));
        for (int i = 0; i < 5; i++) {
            executor.sendMessage(Text.literal("\u00A75" + String.valueOf(5-i) + ". \u00A7d" + SpellData.getSpellName(SpellData.getSpell((IEntityDataSaver) player, 4-i))));
        }
        return 1;
    }

    private static boolean isValidSpell(String spell) {
        for (String str : SpellRegisterer.spellList()) {
            if (str.equals(spell)) {
                return true;
            }
        }
        return false;
    }
}