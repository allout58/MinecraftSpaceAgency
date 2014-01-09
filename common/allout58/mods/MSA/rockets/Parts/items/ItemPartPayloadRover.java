package allout58.mods.MSA.rockets.Parts.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemPartPayloadRover extends ItemPartPayload
{

    public ItemPartPayloadRover(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setUnlocalizedName("payload_rover");
        super.postInit();
    }

    @Override
    public void nbtForFinalizedRocket(NBTTagCompound tag, ItemStack stack)
    {
        super.nbtForFinalizedRocket(tag, stack);
        tag.setInteger("PayloadType", 1);
    }
}
