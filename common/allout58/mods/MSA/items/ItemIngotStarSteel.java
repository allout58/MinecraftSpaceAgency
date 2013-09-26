package allout58.mods.MSA.items;

import allout58.mods.MSA.MSA;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.util.StringUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemIngotStarSteel extends Item
{

    public ItemIngotStarSteel(int par1)
    {
        super(par1);
        setMaxStackSize(64);
        setCreativeTab(MSA.creativeTab);
        setUnlocalizedName("ingotStarSteel");
        setTextureName(MSATextures.RESOURCE_CONTEXT+":" + getUnlocalizedName().substring(5));
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
        return StringUtils.localize(getUnlocalizedName(itemstack));
    }

}
