package allout58.mods.MSA.rockets.Parts.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import allout58.mods.MSA.MSA;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.rockets.RocketEnums.RocketSize;
import allout58.mods.MSA.util.StringUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemRocketPart extends Item
{
    public RocketSize Size = RocketSize.Small;

    public ItemRocketPart(int par1)
    {
        super(par1);
        setCreativeTab(MSA.creativeTab);
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
        return StringUtils.localize("strings." + RocketSize.values()[itemstack.getItemDamage()].toString()) + " " + StringUtils.localize(getUnlocalizedName(itemstack));
    }

    @Override
    public Item setUnlocalizedName(String name)
    {
        return super.setUnlocalizedName("rocketpart." + name);
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
    }

    @Override
    @SideOnly(Side.CLIENT)
    /** Allows items to add custom lines of information to the mouseover description. */
    public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List infoList, boolean par4)
    {
        infoList.add("MSA Rocket Part");
        infoList.add("Size: " + RocketSize.values()[itemStack.getItemDamage()].toString());
    }

    public void postInit()
    {
        // for now, this will be different w/ custom renderers
        setTextureName(MSATextures.RESOURCE_CONTEXT + ":" + getUnlocalizedName().substring(5));
    }

    public void nbtForFinalizedRocket(NBTTagCompound tag, ItemStack stack)
    {
        tag.setInteger("Size", stack.getItemDamage());
    }
}
