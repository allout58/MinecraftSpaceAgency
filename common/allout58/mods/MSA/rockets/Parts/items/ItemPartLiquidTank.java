package allout58.mods.MSA.rockets.Parts.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemPartLiquidTank extends ItemRocketPart
{

    public ItemPartLiquidTank(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setUnlocalizedName("liquid_tank");
        super.postInit();
    }

    @Override
    public void nbtForFinalizedRocket(NBTTagCompound tag, ItemStack stack)
    {
        super.nbtForFinalizedRocket(tag, stack);
        tag.setString("Area", "FuselageIntegral");
    }
}
