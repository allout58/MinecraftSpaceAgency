package allout58.mods.MSA.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import allout58.mods.MSA.MSA;
import allout58.mods.MSA.blocks.logic.ComSatelliteLogic;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.util.IFacingLogic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockComSatellite extends BlockWrenchable
{
    private Icon top, bottom, side, invisible;

    public BlockComSatellite(int par1, Material par2Material)
    {
        super(par1, par2Material);
        setHardness(4.0F);
        setUnlocalizedName("comSatellite");
        setCreativeTab(MSA.creativeTab);
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        if (meta == 0)
        {
            if (side == 1) return this.top;
            if (side == 0) return this.bottom;
            return this.side;
        }
        else return this.top;
    }

    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 0)
        {
            if (side == ForgeDirection.DOWN.ordinal()) return this.bottom;
            if (side == ForgeDirection.UP.ordinal()) return this.top;
            return this.side;
        }
        else if (meta == 1)
        {
            return this.invisible;
        }
        else if (meta == 2)
        {
            if (side == ForgeDirection.UP.ordinal()) return this.top;
            return this.invisible;
        }
        else return this.side;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        this.side = ir.registerIcon(MSATextures.RESOURCE_CONTEXT + ":" + this.getUnlocalizedName().substring(5) + "_side");
        this.bottom = ir.registerIcon(MSATextures.RESOURCE_CONTEXT + ":" + this.getUnlocalizedName().substring(5) + "_bottom");
        this.top = ir.registerIcon(MSATextures.RESOURCE_CONTEXT + ":" + this.getUnlocalizedName().substring(5) + "_top");
        this.invisible = ir.registerIcon(MSATextures.RESOURCE_CONTEXT + ":invisible");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        super.onBlockActivated(world, x, y, z, entityPlayer, par6, par7, par8, par9);
        TileEntity logic = world.getBlockTileEntity(x, y, z);
        if (entityPlayer.isSneaking()) return false;
        if (logic instanceof ComSatelliteLogic)
        {
            ((ComSatelliteLogic) logic).checkValidStructure();
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack par6ItemStack)
    {
        TileEntity logic = world.getBlockTileEntity(x, y, z);
        if (logic instanceof IFacingLogic)
        {
            if (entityLiving == null)
            {
                ((IFacingLogic) logic).setDirection(0F, 0F, null);
            }
            else
            {
                ((IFacingLogic) logic).setDirection(entityLiving.rotationYaw * 4F, entityLiving.rotationPitch, entityLiving);
            }
        }
        if (logic instanceof ComSatelliteLogic)
        {
            ((ComSatelliteLogic) logic).checkValidStructure();
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6)
    {
        TileEntity logic = world.getBlockTileEntity(x, y, z);
        if (logic instanceof ComSatelliteLogic)
        {
            ((ComSatelliteLogic) logic).invalidateStructure();
        }
        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    protected void wrenchBlock(World world, int x, int y, int z)
    {
        TileEntity logic = world.getBlockTileEntity(x, y, z);
        if (logic instanceof ComSatelliteLogic)
        {
            if (!((ComSatelliteLogic) logic).isControlBlock)
            {
                // remove the block from the world and spawn a EntityItem in the
                // world
                // of the block
                Random rand = new Random();
                world.setBlock(x, y, z, 0);
                ((ComSatelliteLogic) logic).invalidateStructure();
                float f = rand.nextFloat() * 0.8F + 0.1F;
                float f1 = rand.nextFloat() * 0.8F + 0.1F;
                float f2 = rand.nextFloat() * 0.8F + 0.1F;
                EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(BlockList.ssBuilding, 1));
                float f3 = 0.05F;
                entityitem.motionX = (double) ((float) rand.nextGaussian() * f3);
                entityitem.motionY = (double) ((float) rand.nextGaussian() * f3 + 0.2F);
                entityitem.motionZ = (double) ((float) rand.nextGaussian() * f3);
                world.spawnEntityInWorld(entityitem);
            }
            else
            {
                super.wrenchBlock(world, x, y, z);
            }
        }
        else
        {
            super.wrenchBlock(world, x, y, z);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new ComSatelliteLogic();
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
