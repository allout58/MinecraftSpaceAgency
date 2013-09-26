package allout58.mods.MSA.Blocks.Logic;

// ~--- non-JDK imports --------------------------------------------------------

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;
import allout58.mods.MSA.MSA;
import allout58.mods.MSA.Blocks.BlockList;
import allout58.mods.MSA.Rockets.Rocket;
import allout58.mods.MSA.Rockets.Entity.EntityRocket;
import allout58.mods.MSA.Rockets.RocketEnums.RocketSize;
import allout58.mods.MSA.util.IFacingLogic;
import allout58.mods.MSA.util.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.MathHelper;

import net.minecraftforge.common.ForgeDirection;

public class LaunchControlLogic extends TileEntity implements IFacingLogic
{
    private static final int[] sizes = new int[] { 3, 5, 7, 8 };// last size not
                                                                // used, extra

    public boolean isValidStructure = false;

    private byte direction = 0;
    private byte size = 0;
    private int height = 0;
    private int flameOutLength = 0;
    private int centerX = 0;
    private int centerY = 0;
    private int centerZ = 0;

    private EntityPlayer launchedBy;

    public Rocket RocketLogic;

    private boolean needsUpdate = false;

    /* Multiblock Structure Checking */

    public boolean checkValidStructure(int x, int y, int z)
    {
        flameOutLength = height = size = 0;
        isValidStructure = checkTowerHeight(x, y, z);
        isValidStructure &= checkLaunchPad(x, y, z);
        isValidStructure &= checkFlameOutlets(x, y, z);

        needsUpdate = true;

        return isValidStructure;
    }

    private boolean checkFlameOutlets(int x, int y, int z)
    {
        boolean isGood = false;
        int halfSide = (int) Math.floor(sizes[size] / 2);
        long[] lengths = new long[] { 0, 0, 0 };
        switch (direction)
        {
            case 2:
                lengths[0] = flamePathLength(centerX - halfSide, centerY, centerZ, ForgeDirection.WEST);
                lengths[1] = flamePathLength(centerX, centerY, centerZ + halfSide, ForgeDirection.SOUTH);
                lengths[2] = flamePathLength(centerX + halfSide, centerY, centerZ, ForgeDirection.EAST);
                break;
            case 3:
                lengths[0] = flamePathLength(centerX - halfSide, centerY, centerZ, ForgeDirection.WEST);
                lengths[1] = flamePathLength(centerX, centerY, centerZ - halfSide, ForgeDirection.NORTH);
                lengths[2] = flamePathLength(centerX + halfSide, centerY, centerZ, ForgeDirection.EAST);
                break;
            case 4:
                lengths[0] = flamePathLength(centerX, centerY, centerZ - halfSide, ForgeDirection.NORTH);
                lengths[1] = flamePathLength(centerX, centerY, centerZ + halfSide, ForgeDirection.SOUTH);
                lengths[2] = flamePathLength(centerX + halfSide, centerY, centerZ, ForgeDirection.EAST);
                break;
            case 5:
                lengths[0] = flamePathLength(centerX - halfSide, centerY, centerZ, ForgeDirection.WEST);
                lengths[1] = flamePathLength(centerX, centerY, centerZ + halfSide, ForgeDirection.SOUTH);
                lengths[2] = flamePathLength(centerX, centerY, centerZ - halfSide, ForgeDirection.NORTH);
                break;
        }
        flameOutLength = (int) MathHelper.average(lengths);
        if (flameOutLength >= 1) isGood = true;
        return isGood;
    }

    private int flamePathLength(int x, int y, int z, ForgeDirection dir)
    {
        int length = 0;
        switch (dir)
        {
            case NORTH:
                while (worldObj.getBlockId(x, y, --z) == Block.stoneSingleSlab.blockID)
                    length++;
                break;
            case SOUTH:
                while (worldObj.getBlockId(x, y, ++z) == Block.stoneSingleSlab.blockID)
                    length++;
                break;
            case EAST:
                while (worldObj.getBlockId(++x, y, z) == Block.stoneSingleSlab.blockID)
                    length++;
                break;
            case WEST:
                while (worldObj.getBlockId(--x, y, z) == Block.stoneSingleSlab.blockID)
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
                            // worldObj.setBlock(x1, y, z1, Block.fire.blockID);
                            if (this.worldObj.getBlockId(x1, y - 1, z1) != BlockList.storageStarSteel.blockID)
                            {
                                isGood = false;
                            }
                        }
                    }
                    centerX = x;
                    centerZ = z + halfSide;
                    break;
                case 3:
                    for (int x1 = x - halfSide; x1 <= x + halfSide && isGood; x1++)
                    {
                        for (int z1 = z - sizes[i]; z1 <= z - 1 && isGood; z1++)
                        {
                            // worldObj.setBlock(x1, y, z1, Block.fire.blockID);
                            if (this.worldObj.getBlockId(x1, y - 1, z1) != BlockList.storageStarSteel.blockID)
                            {
                                isGood = false;
                            }
                        }
                    }
                    centerX = x;
                    centerZ = z - halfSide;
                    break;
                case 4:
                    for (int x1 = x + 1; x1 <= x + sizes[i] && isGood; x1++)
                    {
                        for (int z1 = z - halfSide; z1 <= z + halfSide && isGood; z1++)
                        {
                            // worldObj.setBlock(x1, y, z1, Block.fire.blockID);
                            if (this.worldObj.getBlockId(x1, y - 1, z1) != BlockList.storageStarSteel.blockID)
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
                            // worldObj.setBlock(x1, y+1, z1,
                            // Block.fire.blockID);
                            if (this.worldObj.getBlockId(x1, y - 1, z1) != BlockList.storageStarSteel.blockID)
                            {
                                isGood = false;
                            }
                        }
                    }
                    centerX = x - halfSide;
                    centerZ = z;
                    break;
            }
            centerY = y - 1;
            if (isGood)
            {
                this.size = (byte) i;
            }
            else if (size >= 0)
            {
                return true;
            }
            else return false;
        }
        return false;
    }

    private boolean checkTowerHeight(int x, int y, int z)
    {
        while (worldObj.getBlockId(x, ++y, z) == BlockList.launchTower.blockID)
        {
            height++;
        }
        return (height > 0);
    }

    /* Launching */

    public void LaunchSequence(Rocket rBase)
    {
        if (RocketLogic != null)
        {
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 10, 0, new Packet3Chat(ChatMessageComponent.createFromText("[" + StringUtils.localize("strings.Title") + "] Rocket already launched and not yet gone, please wait.")));
            return;
        }
        RocketLogic = rBase;
        System.out.println("Launching " + RocketLogic.Size.toString() + " size rocket...");
        if (RocketLogic.Size.ordinal() > this.size)
        {
            // explodeLaunchPad();
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 10, 0, new Packet3Chat(ChatMessageComponent.createFromText("[" + StringUtils.localize("strings.Title") + "] The rocket is too big! It's going to blow!")));
            RocketLogic = null;
            // failLaunch();
            return;
        }
        if ((((RocketLogic.Size.ordinal() + 1) ^ 3) * 2) > height)
        {
            // setRocketAccuracy(-.005*size.ordinal()^3*2-height);
            System.out.println(((RocketLogic.Size.ordinal() + 1) ^ 3) * 2);
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 10, 0, new Packet3Chat(ChatMessageComponent.createFromText("[" + StringUtils.localize("strings.Title") + "] Launch tower not high enough. The rocket looses accuracy.")));
        }
        if ((((RocketLogic.Size.ordinal() + 1) ^ 3) * 3) > flameOutLength)
        {
            // flamesAroundEnd()
            System.out.println(((RocketLogic.Size.ordinal() + 1) ^ 3) * 3);
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 10, 0, new Packet3Chat(ChatMessageComponent.createFromText("[" + StringUtils.localize("strings.Title") + "] Flame outlets not long enough. Watch out for flames around the ends!")));
        }
        if (!worldObj.isRemote) worldObj.spawnEntityInWorld(new EntityRocket(worldObj, RocketLogic, centerX + 0.5, centerY + 3.5, centerZ + 0.5, this.xCoord, this.yCoord, this.zCoord));
    }

    /* Utilities */
    
    public void forceUpdate()
    {
        needsUpdate=true;
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
        isValidStructure = tags.getBoolean("IsValidStructure");
        if (tags.hasKey("RocketLogic"))
        {
            RocketLogic = new Rocket();
            RocketLogic.readFromNBT(tags.getCompoundTag("RocketLogic"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tags)
    {
        super.writeToNBT(tags);
        tags.setByte("Direction", direction);
        tags.setBoolean("IsValidStructure", isValidStructure);
        if (RocketLogic != null)
        {
            NBTTagCompound rocketTag = new NBTTagCompound();
            RocketLogic.writeToNBT(rocketTag);
            tags.setCompoundTag("RocketLogic", rocketTag);
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
        if (needsUpdate)
        {
            needsUpdate = false;
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        }
    }
}