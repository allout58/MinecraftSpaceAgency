package allout58.mods.MSA.blocks.container.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import allout58.mods.MSA.items.ItemLinkCard;

public class SlotLinkCard extends Slot
{

    public SlotLinkCard(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return (itemStack.getItem() instanceof ItemLinkCard && itemStack.getItemDamage() == 1);// only
                                                                                               // linked
                                                                                               // linking
                                                                                               // cards
    }
}
