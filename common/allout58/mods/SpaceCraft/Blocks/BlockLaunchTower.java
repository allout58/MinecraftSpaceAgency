package allout58.mods.SpaceCraft.Blocks;

import allout58.mods.SpaceCraft.SpaceCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
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
        setCreativeTab(SpaceCraft.creativeTab);
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
        this.side = ir.registerIcon("SpaceCraft:" + this.getUnlocalizedName().substring(5) + "_side");
        this.bottom = this.top = ir.registerIcon("SpaceCraft:blockStarSteel");
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
