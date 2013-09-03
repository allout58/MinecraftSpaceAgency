package allout58.mods.SpaceCraft.Rockets.Parts.Items;

import allout58.mods.SpaceCraft.SpaceCraft;
import allout58.mods.SpaceCraft.util.StringUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemRocketPart extends Item
{

    public ItemRocketPart(int par1)
    {
        super(par1);
        setCreativeTab(SpaceCraft.creativeTab);
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
        func_111206_d("spacecraft:" + getUnlocalizedName().substring(5));
    }
}
