package allout58.mods.MSA.rockets.Parts.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemPartFuselage extends ItemRocketPart
{
    public ItemPartFuselage(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setUnlocalizedName("fuselage");
        super.postInit();
    }

    @Override
    public void nbtForFinalizedRocket(NBTTagCompound tag, ItemStack stack)
    {
        super.nbtForFinalizedRocket(tag, stack);
        tag.setString("Area", "Fuselage");
    }
}
