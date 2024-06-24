package koffee.magictoffee.spells;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class EmptySpell extends Spell {

    EmptySpell() {
        super.spellID = "magictoffee:empty";
        super.displayName = "Empty Slot";
        super.cooldown = -1;
        super.manaCost = 0;
        super.registerBook = false;
        super.spellBook = ItemStack.EMPTY;
    }

    @Override
    public boolean ActionOnUse(PlayerEntity player){
        player.sendMessage(Text.literal("§7No spell selected"), true);
        return false;
    }

}
