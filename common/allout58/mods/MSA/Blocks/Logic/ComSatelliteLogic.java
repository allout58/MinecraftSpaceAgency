package allout58.mods.MSA.Blocks.Logic;

import java.util.*;

import cpw.mods.fml.common.FMLLog;
import allout58.mods.MSA.Blocks.BlockList;
import allout58.mods.MSA.Rockets.Rocket;
import allout58.mods.MSA.util.IFacingLogic;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

public class ComSatelliteLogic extends TileEntity implements IFacingLogic
{
    public boolean isControlBlock = false;
    public boolean isValidStructure = false;

    private byte direction = 0;
    private boolean needsUpdate = false;
    private int MasterPos[] = { 0, 0, 0 };

    public int[] getMasterPos()
    {
        return MasterPos;
    }
    protected void setMasterPos(int masterPos[])
    {
        MasterPos = masterPos;
    }

    // slave only
    public void setMaster(ComSatelliteLogic MasterLogic)
    {
        if (!this.isControlBlock)
        {
            this.setMasterPos(new int[] { MasterLogic.xCoord, MasterLogic.yCoord, MasterLogic.zCoord });
        }
        else
        {
            FMLLog.severe("Master com satellite block tried to set a master for itself");
        }
    }

    /* master only */

    public boolean checkValidStructure()
    {
        if (!isValidStructure)
        {
            isValidStructure = true;
            isControlBlock = true;
            for (int y1 = yCoord - 1; y1 <= yCoord && isValidStructure; y1++)
            {
                for (int x1 = xCoord - 1; x1 <= xCoord + 1 && isValidStructure; x1++)
                {
                    for (int z1 = zCoord - 1; z1 <= zCoord + 1; z1++)
                    {
                        // ignore the control block when calculating valid
                        // structure;
                        if (x1 == xCoord && y1 == yCoord && z1 == zCoord) continue;
                        if (worldObj.getBlockId(x1, y1, z1) != BlockList.storageStarSteel.blockID) isValidStructure = false;
                    }
                }
            }
            if (isValidStructure)
            {
                convertBlocks();
                needsUpdate = true;
            }
        }
        return isValidStructure;
    }

    public void convertBlocks()
    {
        // set up this as the master block
        this.isControlBlock = true;
        this.setMasterPos(new int[] { this.xCoord, this.yCoord, this.zCoord });
        // change all the slaves
        for (int y1 = yCoord - 1; y1 <= yCoord; y1++)
        {
            for (int x1 = xCoord - 1; x1 <= xCoord + 1; x1++)
            {
                for (int z1 = zCoord - 1; z1 <= zCoord + 1; z1++)
                {
                    if (x1 == xCoord && y1 == yCoord && z1 == zCoord)
                    {
                        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 3);
                        TileEntity newlySlave = worldObj.getBlockTileEntity(x1, y1, z1);
                        continue;
                    }
                    // just double checking
                    if (worldObj.getBlockId(x1, y1, z1) == BlockList.storageStarSteel.blockID)
                    {
                        worldObj.setBlock(x1, y1, z1, BlockList.comSatellite.blockID, 1, 3);
                        TileEntity newlySlave = worldObj.getBlockTileEntity(x1, y1, z1);
                        if (newlySlave instanceof ComSatelliteLogic)
                        {
                            ((ComSatelliteLogic) newlySlave).setMaster(this);
                            // ((ComSatelliteLogic) newlySlave).isValidStructure
                            // = true;
                        }
                        else
                        {
                            FMLLog.severe("The newly created slave at %d, %d, %d is not of the correct type!", x1, y1, z1);
                        }
                    }
                }
            }
        }
    }

    public void revertBlocks()
    {
        if (!isControlBlock) return;
        if (isValidStructure)
        {
            this.isValidStructure = false;
            for (int y1 = yCoord - 1; y1 <= yCoord; y1++)
            {
                for (int x1 = xCoord - 1; x1 <= xCoord + 1; x1++)
                {
                    for (int z1 = zCoord - 1; z1 <= zCoord + 1; z1++)
                    {
                        if (x1 == xCoord && y1 == yCoord && z1 == zCoord)
                        {
                            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
                            continue;
                        }
                        // just double checking
                        if (worldObj.getBlockId(x1, y1, z1) == BlockList.comSatellite.blockID && worldObj.getBlockMetadata(x1, y1, z1) == 1)
                        {
                            worldObj.setBlock(x1, y1, z1, BlockList.storageStarSteel.blockID);
                        }
                    }
                }
            }
            needsUpdate = true;
        }
    }

    /* both */
    public void invalidateStructure()
    {

        if (!isControlBlock)
        {
            this.getMaster().revertBlocks();
            this.getMaster().needsUpdate = true;
        }
        else
        {
            this.revertBlocks();
            this.needsUpdate = true;
        }
    }

    public ComSatelliteLogic getMaster()
    {
        if (worldObj == null) return null;
        TileEntity logic = worldObj.getBlockTileEntity(getMasterPos()[0], getMasterPos()[1], getMasterPos()[2]);
        if (logic instanceof ComSatelliteLogic)
        {
            return (ComSatelliteLogic) logic;
        }
        else
        {
            return null;
        }
    }

    public boolean hasMaster()
    {
        return getMaster() != null;
    }

    /* Utilities */
    public void forceUpdate()
    {
        needsUpdate = true;
    }

    /* IFacingLogic */

    @Override
    public byte getRenderDirection()
    {
        return direction;
    }

    @Override
    public ForgeDirection getForgeDirection()
    {
        return ForgeDirection.VALID_DIRECTIONS[direction];
    }

    @Override
    public void setDirection(int side)
    {
    }

    @Override
    public void setDirection(float yaw, float pitch, EntityLivingBase player)
    {
        int facing = MathHelper.floor_double((double) (yaw / 360) + 0.5D) & 3;
        switch (facing)
        {
            case 0:
                direction = 2;
                break;

            case 1:
                direction = 5;
                break;

            case 2:
                direction = 3;
                break;

            case 3:
                direction = 4;
                break;
        }
    }

    /* NBT */

    @Override
    public void readFromNBT(NBTTagCompound tags)
    {
        super.readFromNBT(tags);
        direction = tags.getByte("Direction");
        isControlBlock = tags.getBoolean("isControlBlock");
        isValidStructure = tags.getBoolean("isValidStructure");
        if (tags.hasKey("masterPos"))
        {
            setMasterPos(tags.getIntArray("masterPos"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tags)
    {
        super.writeToNBT(tags);
        tags.setByte("Direction", direction);
        tags.setBoolean("isControlBlock", isControlBlock);
        tags.setBoolean("isValidStructure", isValidStructure);
        if (!isControlBlock)
        {
            tags.setIntArray("masterPos", getMasterPos());
        }
    }

    /* Packets */
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
    {
        readFromNBT(packet.data);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void updateEntity()
    {
        // if (isControlBlock)
        // {
        // worldObj.spawnParticle("fire", xCoord + 0.5, yCoord + 1.01, zCoord +
        // 0.5, 0.01, 0.01, 0.01);
        // }
        if (needsUpdate)
        {
            needsUpdate = false;
            System.out.println("Updating " + (isControlBlock ? "Master" : "Slave") + " block.");
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        }
    }
}
