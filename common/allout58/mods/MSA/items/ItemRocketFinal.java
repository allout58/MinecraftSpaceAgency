package allout58.mods.MSA.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import allout58.mods.MSA.MSA;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.rockets.RocketEnums.RocketSize;
import allout58.mods.MSA.util.StringUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRocketFinal extends Item
{

    public ItemRocketFinal(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setCreativeTab(MSA.creativeTab);
        setUnlocalizedName("rocketFinal");
        setTextureName(MSATextures.RESOURCE_CONTEXT + ":" + getUnlocalizedName().substring(5));
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
        return StringUtils.localize(getUnlocalizedName(itemstack));
    }

    @Override
    @SideOnly(Side.CLIENT)
    /** Allows items to add custom lines of information to the mouseover description. */
    public void addInformation(ItemStack stack, EntityPlayer entityPlayer, List infoList, boolean par4)
    {
        if (stack.stackTagCompound != null)
        {
            infoList.add("Size: " + RocketSize.values()[stack.stackTagCompound.getCompoundTag("Fuselage").getInteger("Size")].toString());
        }
        else
        {
            infoList.add("Not finalized correctly!");
            infoList.add("This will do nothing in the launcher.");
        }
    }
}
