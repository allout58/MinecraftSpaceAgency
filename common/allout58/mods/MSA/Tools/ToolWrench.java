package allout58.mods.MSA.Tools;

import buildcraft.api.tools.IToolWrench;
import allout58.mods.MSA.MSA;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.util.StringUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ToolWrench extends Item implements IToolWrench
{

    public ToolWrench(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setCreativeTab(MSA.creativeTab);
        setUnlocalizedName("wrench");
        setTextureName(MSATextures.RESOURCE_CONTEXT + ":" + getUnlocalizedName().substring(5));
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
        return StringUtils.localize(getUnlocalizedName(itemstack));
    }

    @Override
    public boolean canWrench(EntityPlayer player, int x, int y, int z)
    {
        return true;
    }

    @Override
    public void wrenchUsed(EntityPlayer player, int x, int y, int z)
    {

    }

    @Override
    public boolean shouldPassSneakingClickToBlock(World par2World, int par4, int par5, int par6)
    {
        return true;
    }
}
