package allout58.mods.SpaceCraft.Blocks;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import allout58.mods.SpaceCraft.SpaceCraft;
import allout58.mods.SpaceCraft.Blocks.Logic.LaunchControlLogic;
import allout58.mods.SpaceCraft.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLaunchControl extends BlockWrenchable
{
    private Icon top, bottom, side, frontBroke, front;

    public BlockLaunchControl(int par1, Material par2Material)
    {
        super(par1, par2Material);
        setUnlocalizedName("launchControl");
        setCreativeTab(SpaceCraft.creativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        if (side == 1) return this.top;
        if (side == 0) return this.bottom;
        return this.side;
    }

    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
        TileEntity logic = world.getBlockTileEntity(x, y, z);
        short direction = (logic instanceof IFacingLogic) ? ((IFacingLogic) logic).getRenderDirection() : 0;
        if (side == direction)
        {
            return this.front;
        }
        return this.side;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        this.side = ir.registerIcon("SpaceCraft:" + this.getUnlocalizedName().substring(5) + "_side");
        this.bottom = this.top = ir.registerIcon("SpaceCraft:blockStarSteel");
        this.front = ir.registerIcon("furnace_side");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        super.onBlockActivated(world, x, y, z, entityPlayer, par6, par7, par8, par9);
        if (world.isRemote)
        {
            TileEntity te = world.getBlockTileEntity(x, y, z);
            if (te instanceof LaunchControlLogic)
            {
                if (((LaunchControlLogic) te).checkValidStructure(x, y, z))
                {
                    entityPlayer.addChatMessage("Valid structure for Launch Control");
                }
                else entityPlayer.addChatMessage("Invalid structure for Launch Control");
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new LaunchControlLogic();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack par6ItemStack)
    {
        TileEntity logic = world.getBlockTileEntity(x, y, z);
        if (logic instanceof IFacingLogic)
        {
            IFacingLogic direction = (IFacingLogic) logic;
            if (entityLiving == null)
            {
                direction.setDirection(0F, 0F, null);
            }
            else
            {
                direction.setDirection(entityLiving.rotationYaw * 4F, entityLiving.rotationPitch, entityLiving);
            }
        }
    }
}
