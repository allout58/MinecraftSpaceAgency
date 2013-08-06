package allout58.mods.SpaceCraft.Blocks.Logic;

// ~--- non-JDK imports --------------------------------------------------------

import cpw.mods.fml.common.FMLLog;
import allout58.mods.SpaceCraft.SpaceCraft;
import allout58.mods.SpaceCraft.util.IFacingLogic;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import net.minecraftforge.common.ForgeDirection;

public class LaunchControlLogic extends TileEntity implements IFacingLogic
{
    private static final int[] sizes = new int[] { 3, 5, 7 };

    public boolean isValidStructure = false;

    private byte direction = 0;
    private byte size = 0;
    private int height = 0;
    private int flameOutLength = 0;
    private int centerX = 0;
    private int centerY = 0;
    private int centerZ = 0;

    private boolean isLoaded = true;

    public boolean checkValidStructure(int x, int y, int z)
    {
        flameOutLength = height = size = 0;
        isValidStructure = checkTowerHeight(x, y, z);
        isValidStructure &= checkLaunchPad(x, y, z);
        isValidStructure &= checkFlameOutlets(x, y, z);

        return isValidStructure;
    }

    private boolean checkFlameOutlets(int x, int y, int z)
    {
        boolean isGood=false;
        int halfSide=(int) Math.floor(sizes[size]/2);
        long[] lengths=new long[] {0,0,0};
        switch (direction)
        {
            case 2:
                lengths[0]=flamePathLength(centerX-halfSide,centerY,centerZ,ForgeDirection.WEST);
                lengths[1]=flamePathLength(centerX,centerY,centerZ+halfSide,ForgeDirection.SOUTH);
                lengths[2]=flamePathLength(centerX+halfSide, centerY, centerZ, ForgeDirection.EAST);
                break;
            case 3:
                lengths[0]=flamePathLength(centerX-halfSide,centerY,centerZ,ForgeDirection.WEST);
                lengths[1]=flamePathLength(centerX,centerY,centerZ-halfSide,ForgeDirection.NORTH);
                lengths[2]=flamePathLength(centerX+halfSide, centerY, centerZ, ForgeDirection.EAST);
                break;
            case 4:
                lengths[0]=flamePathLength(centerX,centerY,centerZ-halfSide,ForgeDirection.NORTH);
                lengths[1]=flamePathLength(centerX,centerY,centerZ+halfSide,ForgeDirection.SOUTH);
                lengths[2]=flamePathLength(centerX+halfSide, centerY, centerZ, ForgeDirection.EAST);
                break;
            case 5:
                lengths[0]=flamePathLength(centerX-halfSide,centerY,centerZ,ForgeDirection.WEST);
                lengths[1]=flamePathLength(centerX,centerY,centerZ+halfSide,ForgeDirection.SOUTH);
                lengths[2]=flamePathLength(centerX, centerY, centerZ-halfSide, ForgeDirection.NORTH);
                break;
        }
        if(MathHelper.average(lengths)>=1)isGood=true;
        return isGood;
    }

    private int flamePathLength(int x, int y, int z, ForgeDirection dir)
    {
        int length = 0;
        switch (dir)
        {
            case NORTH:
                while (worldObj.getBlockId(x, y, z--) == Block.stoneSingleSlab.blockID)
                    length++;
                break;
            case SOUTH:
                while (worldObj.getBlockId(x, y, z++) == Block.stoneSingleSlab.blockID)
                    length++;
                break;
            case EAST:
                while (worldObj.getBlockId(x++, y, z) == Block.stoneSingleSlab.blockID)
                    length++;
                break;
            case WEST:
                while (worldObj.getBlockId(x--, y, z) == Block.stoneSingleSlab.blockID)
                    length++;
                break;
            default:
                return -1;
        }
        return length;
    }

    private boolean checkLaunchPad(int x, int y, int z)
    {
        for (int i = 0; i < sizes.length; i++)
        {
            boolean isGood = true;
            int halfSide = (int) Math.floor(sizes[i] / 2);
            switch (direction)
            {
                case 2:
                    for (int x1 = x - halfSide; x1 <= x + halfSide && isGood; x1++)
                    {
                        for (int z1 = z + 1; z1 <= z + sizes[i] && isGood; z1++)
                        {

                            if (this.worldObj.getBlockId(x1, y - 1, z1) != SpaceCraft.storageStarSteel.blockID)
                            {
                                isGood = false;
                            }
                        }
                    }
                    centerX=x;
                    centerZ=z+halfSide;
                    break;
                case 3:
                    for (int x1 = x - halfSide; x1 <= x + halfSide && isGood; x1++)
                    {
                        for (int z1 = z - sizes[i]; z1 <= z - 1 && isGood; z1++)
                        {

                            if (this.worldObj.getBlockId(x1, y - 1, z1) != SpaceCraft.storageStarSteel.blockID)
                            {
                                isGood = false;
                            }
                        }
                    }
                    centerX=x;
                    centerZ=z-halfSide;
                    break;
                case 4:
                    for (int x1 = x + 1; x1 <= x + sizes[i] && isGood; x1++)
                    {
                        for (int z1 = z - halfSide; z1 <= z + halfSide && isGood; z1++)
                        {

                            if (this.worldObj.getBlockId(x1, y - 1, z1) != SpaceCraft.storageStarSteel.blockID)
                            {
                                isGood = false;
                            }
                        }
                    }
                    centerX = x + halfSide;
                    centerZ = z;
                    break;

                case 5:
                    for (int x1 = x - sizes[i]; x1 <= x - 1 && isGood; x1++)
                    {
                        for (int z1 = z - halfSide; z1 <= z + halfSide && isGood; z1++)
                        {

                            if (this.worldObj.getBlockId(x1, y - 1, z1) != SpaceCraft.storageStarSteel.blockID)
                            {
                                isGood = false;
                            }
                        }
                    }
                    centerX = x - halfSide;
                    centerZ = z;
                    break;
            }
            if (isGood)
            {
                this.size += 1;
            }
            else if (size > 0)
            {
                centerY = y - 1;
                return true;
            }
            else return false;
        }
        return false;
    }

    private boolean checkTowerHeight(int x, int y, int z)
    {
        while (worldObj.getBlockId(x, ++y, z) == SpaceCraft.launchTower.blockID)
        {
            height++;
        }
        return (height > 0);
    }

    @Override
    public byte getRenderDirection()
    {
        return direction;
    }

    /* NBT */

    @Override
    public void readFromNBT(NBTTagCompound tags)
    {
        super.readFromNBT(tags);
        direction = tags.getByte("Direction");
        isLoaded = false;
    }

    @Override
    public void writeToNBT(NBTTagCompound tags)
    {
        super.writeToNBT(tags);
        tags.setByte("Direction", direction);
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
        readFromNBT(packet.customParam1);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void updateEntity()
    {
        if (!isLoaded)
        {
            // worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
            isLoaded = true;
        }
    }
}