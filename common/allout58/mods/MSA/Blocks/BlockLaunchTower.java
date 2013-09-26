package allout58.mods.MSA.Blocks;

import allout58.mods.MSA.MSA;
import allout58.mods.MSA.constants.MSATextures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockLaunchTower extends Block
{

    public Icon side, top, bottom;

    public BlockLaunchTower(int par1, Material par2Material)
    {
        super(par1, par2Material);
        setUnlocalizedName("launchTower");
        setHardness(1.5F);
        setCreativeTab(MSA.creativeTab);
        //walk through
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int x, int y, int z)
    {
        return AxisAlignedBB.getAABBPool().getAABB((double)x + 0.1D, (double)y, (double)z + 0.1D, (double)x + 0.9D, (double)y + 1.0D, (double)z + 0.9D);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        if (side == 1) return this.top;
        if (side == 0) return this.bottom;
        else return this.side;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        this.side = ir.registerIcon(MSATextures.RESOURCE_CONTEXT+":" + this.getUnlocalizedName().substring(5) + "_side");
        this.bottom = this.top = ir.registerIcon(MSATextures.RESOURCE_CONTEXT+":blockStarSteel");
    }

    @Override
    @SideOnly(Side.CLIENT)
    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    public int getRenderBlockPass()
    {
        return 0; 
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isLadder(World world, int x, int y, int z, EntityLivingBase entity)
    {
        return true;
    }
}
