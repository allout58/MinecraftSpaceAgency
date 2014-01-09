package allout58.mods.MSA.blocks.container.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import allout58.mods.MSA.rockets.Parts.items.ItemPartFuselage;

public class SlotFuselage extends Slot
{

    public SlotFuselage(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.getItem() instanceof ItemPartFuselage;
    }
}
