package allout58.mods.SpaceCraft.Items;

import allout58.mods.SpaceCraft.SpaceCraft;
import allout58.mods.SpaceCraft.util.StringUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemIngotStarSteel extends Item
{

    public ItemIngotStarSteel(int par1)
    {
        super(par1);
        setMaxStackSize(64);
        setCreativeTab(SpaceCraft.creativeTab);
        setUnlocalizedName("ingotStarSteel");
        func_111206_d("spacecraft:" + getUnlocalizedName().substring(5));
    }
    
    @Override
    public String getItemDisplayName(ItemStack itemstack) {
        return StringUtils.localize(getUnlocalizedName(itemstack));
    }

}
