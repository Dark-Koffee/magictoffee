package koffee.magictoffee.spells;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class EmptySpell extends Spell {

    EmptySpell() {
        super.spellID = "magictoffee:empty";
        super.displayName = "Empty Slot";
    }

    @Override
    public void ActionOnUse(PlayerEntity user){
        user.sendMessage(Text.literal("\u00A77No spell selected"), true);
    }

}
