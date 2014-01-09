package allout58.mods.MSA.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import allout58.mods.MSA.MSA;
import allout58.mods.MSA.blocks.logic.ILinkable;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.util.StringUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLinkCard extends Item
{
    private Icon linked, unlinked;

    public ItemLinkCard(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setCreativeTab(MSA.creativeTab);
        setUnlocalizedName("linkCard");
        setTextureName(MSATextures.RESOURCE_CONTEXT + ":" + getUnlocalizedName().substring(5) + ".unlinked");
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
        return StringUtils.localize(getUnlocalizedName(itemstack)) + " " + StringUtils.localize("strings." + ((itemstack.getItemDamage() == 1) ? "Linked" : "Unlinked"));
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (player.isSneaking())
        {
            stack.stackTagCompound = null;
            this.setDamage(stack, 0);
        }
        return stack;
    }

    // private boolean isLinkable(Class<? extends Block> cls)
    // {
    // for (Class<? extends Block> tech : BlockList.techNet)
    // {
    // if (tech.isAssignableFrom(cls)) return true;
    // }
    // return false;
    // }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        TileEntity logic = world.getBlockTileEntity(x, y, z);

        if (logic == null) return false;

        if (logic instanceof ILinkable)
        {
            ((ILinkable) logic).Link(stack);
            return !world.isRemote;
        }
        else return false;
    }

    @Override
    public Icon getIconFromDamage(int damage)
    {
        if (damage == 1) return this.linked;
        return this.unlinked;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        this.linked = ir.registerIcon(MSATextures.RESOURCE_CONTEXT + ":" + getUnlocalizedName().substring(5) + ".linked");
        this.unlinked = ir.registerIcon(MSATextures.RESOURCE_CONTEXT + ":" + getUnlocalizedName().substring(5) + ".unlinked");
    }

    @Override
    @SideOnly(Side.CLIENT)
    /** Allows items to add custom lines of information to the mouseover description. */
    public void addInformation(ItemStack stack, EntityPlayer entityPlayer, List infoList, boolean par4)
    {
        infoList.add("Linked: " + (stack.getItemDamage() == 1 ? "True" : "False"));
        if (stack.stackTagCompound != null) infoList.add(String.format("X: %d, Y: %d, Z: %d", stack.stackTagCompound.getInteger("targetX"), stack.stackTagCompound.getInteger("targetY"), stack.stackTagCompound.getInteger("targetZ")));
    }
}
