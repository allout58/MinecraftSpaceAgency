package allout58.mods.MSA.Blocks.Container.Slots;

import allout58.mods.MSA.Rockets.Parts.Items.ItemPartEngine;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotEngine extends Slot
{

    public SlotEngine(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.getItem() instanceof ItemPartEngine;
    }
}
