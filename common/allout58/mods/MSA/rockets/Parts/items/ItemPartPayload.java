package allout58.mods.MSA.rockets.Parts.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ItemPartPayload extends ItemRocketPart
{
    public ItemPartPayload(int par1)
    {
        super(par1);
    }

    @Override
    public void nbtForFinalizedRocket(NBTTagCompound tag, ItemStack stack)
    {
        super.nbtForFinalizedRocket(tag, stack);
        tag.setString("Area", "Payload");
    }
}
