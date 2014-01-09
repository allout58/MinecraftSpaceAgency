package allout58.mods.MSA.rockets.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import allout58.mods.MSA.blocks.logic.LaunchControlLogic;
import allout58.mods.MSA.rockets.Rocket;
import allout58.mods.MSA.util.StringUtils;
import cpw.mods.fml.common.network.PacketDispatcher;

public class EntityRocket2 extends Entity
{
    public int[] ControlPos = new int[3];

    public EntityRocket2(World par1World)
    {
        super(par1World);
    }

    public EntityRocket2(World par1World, Rocket rLogic, double x, double y, double z, int controlX, int controlY, int controlZ)
    {
        this(par1World);
        this.setPosition(x, y, z);
        ControlPos[0] = controlX;
        ControlPos[1] = controlY;
        ControlPos[2] = controlZ;
        PacketDispatcher.sendPacketToAllAround(controlX, controlY, controlZ, 10, 0, new Packet3Chat(ChatMessageComponent.createFromText("[" + StringUtils.localize("strings.Title") + "] Rocket entity initialized!")));
    }

    @Override
    protected void entityInit()
    {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        NBTTagCompound subTags = nbttagcompound.getCompoundTag("CustomRocketData");
        ControlPos = subTags.getIntArray("ControlPos");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        NBTTagCompound tags = new NBTTagCompound();
        tags.setIntArray("ControlPos", ControlPos);
        nbttagcompound.setTag("CustomRocketData", tags);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if (ticksExisted > 100) this.kill();
        if (!this.worldObj.isRemote)
        {
            this.motionY = .1D;
        }
        setPosition(posX + motionX, posY + motionY, posZ + motionZ);
    }

    public void kill()
    {
        super.kill();
        TileEntity control = worldObj.getBlockTileEntity(ControlPos[0], ControlPos[1], ControlPos[2]);
        if (control instanceof LaunchControlLogic)
        {
            ((LaunchControlLogic) control).RocketLogic = null;
        }
    }
}
