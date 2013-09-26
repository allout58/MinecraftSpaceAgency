package allout58.mods.MSA.Rockets.Parts.Items;

import allout58.mods.MSA.MSA;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.util.StringUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemRocketPart extends Item
{

    public ItemRocketPart(int par1)
    {
        super(par1);
        setCreativeTab(MSA.creativeTab);
    }
    
    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
        return StringUtils.localize(getUnlocalizedName(itemstack));
    }
    
    @Override
    public Item setUnlocalizedName(String name)
    {
        return super.setUnlocalizedName("rocketpart."+name);
    }
    
    public void postInit()
    {
        //for now, this will be different w/ custom renderers
        setTextureName(MSATextures.RESOURCE_CONTEXT+":" + getUnlocalizedName().substring(5));
    }
}
