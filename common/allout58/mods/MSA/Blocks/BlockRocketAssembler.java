package allout58.mods.MSA.Blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import allout58.mods.MSA.MSA;
import allout58.mods.MSA.Blocks.Logic.AssemblerLogic;
import allout58.mods.MSA.constants.MSAGuiIDs;
import allout58.mods.MSA.util.IFacingLogic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRocketAssembler extends BlockWrenchable
{
    private Icon top, bottom, side, front;

    public BlockRocketAssembler(int par1, Material par2Material)
    {
        super(par1, par2Material);
        setUnlocalizedName("rocketAssembler");
        setCreativeTab(MSA.creativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        if (side == 1) return this.top;
        if (side == 0) return this.bottom;
        if (side == 2) return this.front;
        return this.side;
    }

    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
        TileEntity logic = world.getBlockTileEntity(x, y, z);
        short direction = (logic instanceof IFacingLogic) ? ((IFacingLogic) logic).getRenderDirection() : 0;
        if (side == direction) return this.front;
        if (side == ForgeDirection.DOWN.ordinal()) return this.bottom;
        if (side == ForgeDirection.UP.ordinal()) return this.top;
        return this.side;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        this.side = ir.registerIcon("SpaceCraft:" + this.getUnlocalizedName().substring(5) + "_side");
        this.bottom = ir.registerIcon("SpaceCraft:" + this.getUnlocalizedName().substring(5) + "_bottom");
        this.top = ir.registerIcon("SpaceCraft:" + this.getUnlocalizedName().substring(5) + "_top");
        this.front = ir.registerIcon("SpaceCraft:" + this.getUnlocalizedName().substring(5) + "_front");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        super.onBlockActivated(world, x, y, z, entityPlayer, par6, par7, par8, par9);
        if (entityPlayer.isSneaking()) return false;
        else
        {
            if (!world.isRemote)
            {
                AssemblerLogic logic = (AssemblerLogic) world.getBlockTileEntity(x, y, z);

                if (logic != null)
                {
                    entityPlayer.openGui(MSA.instance, MSAGuiIDs.ROCKET_ASSEMBLER, world, x, y, z);
                }
            }

            return true;
        }
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
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new AssemblerLogic();
    }

}
