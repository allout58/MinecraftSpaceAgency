package allout58.mods.MSA.rockets.Parts.items;

import java.util.List;

import allout58.mods.MSA.util.StringUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPartFin extends ItemRocketPart
{

    public ItemPartFin(int par1)
    {
        super(par1);
        setMaxStackSize(4);
        setUnlocalizedName("fin");
        super.postInit();
    }

    @Override
    @SideOnly(Side.CLIENT)
    /** Allows items to add custom lines of information to the mouseover description. */
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List infoList, boolean par4)
    {
        if (StringUtils.shouldAddAdditionalInfo())
        {
            infoList.add("Weight: 10");
            infoList.add("Use: Adds stability to the rocket.");
        }
        else infoList.add(StringUtils.additionalInfoInstructions());
    }

    @Override
    public void nbtForFinalizedRocket(NBTTagCompound tag, ItemStack stack)
    {
        super.nbtForFinalizedRocket(tag, stack);
        tag.setString("Area", "Addon");
    }
}
