package allout58.mods.SpaceCraft.Blocks.Logic;

import allout58.mods.SpaceCraft.util.IFacingLogic;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;

public class AssemblerLogic extends TileEntity implements IFacingLogic
{
    private byte direction = 0;

    private boolean needsUpdate = false;

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
    }

    @Override
    public void writeToNBT(NBTTagCompound tags)
    {
        super.writeToNBT(tags);
        tags.setByte("Direction", direction);
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
        if (needsUpdate)
        {
            needsUpdate = false;
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        }
    }
}
