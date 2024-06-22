package koffee.magictoffee.spells;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class EmptySpell extends Spell {

    EmptySpell() {
        super.spellID = "magictoffee:empty";
        super.displayName = "Empty Slot";
        super.cooldown = 0;
        super.manaCost = 0;
    }

    @Override
    public boolean ActionOnUse(PlayerEntity user){
        user.sendMessage(Text.literal("\u00A77No spell selected"), true);
        return false;
    }

}
