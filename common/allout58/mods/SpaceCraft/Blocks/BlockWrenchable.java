package allout58.mods.SpaceCraft.Blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import buildcraft.api.tools.*;

public abstract class BlockWrenchable extends BlockContainer
{

    private int BlockActivatedCount = 0;

    public BlockWrenchable(int par1, Material par2Material)
    {
        super(par1, par2Material);
        setHardness(20F);
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        super.onBlockActivated(world, x, y, z, entityPlayer, par6, par7, par8, par9);
        if (!world.isRemote)
        {
            Item equipped = entityPlayer.getCurrentEquippedItem() != null ? entityPlayer.getCurrentEquippedItem().getItem() : null;
            if (equipped instanceof IToolWrench && ((IToolWrench) equipped).canWrench(entityPlayer, x, y, z) && entityPlayer.isSneaking())
            {
                // world.markBlockForUpdate(x, y, z);
                ((IToolWrench) equipped).wrenchUsed(entityPlayer, x, y, z);
                this.wrenchBlock(world, x, y, z);
                return true;
            }
        }
        return false;
    }

    public void wrenchBlock(World world, int x, int y, int z)
    {
        Random rand = new Random();
        world.setBlock(x, y, z, 0);
        float f = rand.nextFloat() * 0.8F + 0.1F;
        float f1 = rand.nextFloat() * 0.8F + 0.1F;
        float f2 = rand.nextFloat() * 0.8F + 0.1F;
        EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(this, 1));
        float f3 = 0.05F;
        entityitem.motionX = (double) ((float) rand.nextGaussian() * f3);
        entityitem.motionY = (double) ((float) rand.nextGaussian() * f3 + 0.2F);
        entityitem.motionZ = (double) ((float) rand.nextGaussian() * f3);
        world.spawnEntityInWorld(entityitem);
    }

}
