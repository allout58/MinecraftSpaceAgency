package allout58.mods.MSA.rockets.Parts.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemPartLiquidEngine extends ItemPartEngine
{
    public ItemPartLiquidEngine(int par1)
    {
        super(par1);
        setMaxStackSize(4);
        setUnlocalizedName("liquid_engine");
        super.postInit();
    }

    @Override
    public void nbtForFinalizedRocket(NBTTagCompound tag, ItemStack stack)
    {
        super.nbtForFinalizedRocket(tag, stack);
        tag.setInteger("EngineType", 2);
    }
}
