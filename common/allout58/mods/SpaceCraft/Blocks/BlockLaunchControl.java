package allout58.mods.SpaceCraft.Blocks;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import allout58.mods.SpaceCraft.SpaceCraft;
import allout58.mods.SpaceCraft.Blocks.Logic.LaunchControlLogic;
import allout58.mods.SpaceCraft.Rockets.Rocket;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.RocketSize;
import allout58.mods.SpaceCraft.Rockets.Parts.Logic.EngineBase;
import allout58.mods.SpaceCraft.Rockets.Parts.Logic.Fuselage;
import allout58.mods.SpaceCraft.Rockets.Parts.Logic.SolidFueledEngine;
import allout58.mods.SpaceCraft.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.network.*;

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
        if (side == 2) return this.frontBroke;
        return this.side;
    }

    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
        TileEntity logic = world.getBlockTileEntity(x, y, z);
        short direction = (logic instanceof IFacingLogic) ? ((IFacingLogic) logic).getRenderDirection() : 0;
        if (side == direction)
        {
            if (((LaunchControlLogic) logic).isValidStructure) return this.front;
            return this.frontBroke;
        }
        if (side == ForgeDirection.DOWN.ordinal()) return this.bottom;
        if (side == ForgeDirection.UP.ordinal()) return this.top;
        return this.side;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister ir)
    {
        this.side = ir.registerIcon("SpaceCraft:" + this.getUnlocalizedName().substring(5) + "_side");
        this.bottom = this.top = ir.registerIcon("SpaceCraft:blockStarSteel");
        this.front = ir.registerIcon("SpaceCraft:" + this.getUnlocalizedName().substring(5) + "_front");
        this.frontBroke = ir.registerIcon("SpaceCraft:" + this.getUnlocalizedName().substring(5) + "_front_broke");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        super.onBlockActivated(world, x, y, z, entityPlayer, par6, par7, par8, par9);

        TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te instanceof LaunchControlLogic)
        {
            // check the multi-block one last time before trying to launch
            ((LaunchControlLogic) te).checkValidStructure(x, y, z);
            // this code will be removed
            EngineBase[] engines = new SolidFueledEngine[2];
            engines[0] = new SolidFueledEngine(RocketSize.Small);
            engines[1] = new SolidFueledEngine(RocketSize.Small);
            Fuselage fuselage = new Fuselage();
            fuselage.Size = RocketSize.Medium;
            // end code removal
            ((LaunchControlLogic) te).LaunchSequence(new Rocket(engines, fuselage, null));
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
            if (entityLiving == null)
            {
                ((IFacingLogic) logic).setDirection(0F, 0F, null);
            }
            else
            {
                ((IFacingLogic) logic).setDirection(entityLiving.rotationYaw * 4F, entityLiving.rotationPitch, entityLiving);
            }
            if (logic instanceof LaunchControlLogic)
            {
                ((LaunchControlLogic) logic).checkValidStructure(x, y, z);
            }
        }
    }
}
