package allout58.mods.MSA.rockets.entity;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import allout58.mods.MSA.MSA;
import allout58.mods.MSA.blocks.logic.LaunchControlLogic;
import allout58.mods.MSA.rockets.Rocket;
import allout58.mods.MSA.util.StringUtils;
import cpw.mods.fml.common.network.PacketDispatcher;

public class EntityRocket extends Entity
{
    Rocket rocketLogic;
    boolean firstTick = true;
    public int[] ControlPos = new int[3];

    public EntityRocket(World world)
    {
        super(world);
        this.preventEntitySpawning = true;
    }

    public EntityRocket(World par1World, Rocket rLogic, double x, double y, double z, int controlX, int controlY, int controlZ)
    {
        this(par1World);
        this.setPosition(x, y, z);
        ControlPos[0] = controlX;
        ControlPos[1] = controlY;
        ControlPos[2] = controlZ;
        rocketLogic = rLogic;
        rocketLogic.Launch();
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        NBTTagCompound subTags = nbttagcompound.getCompoundTag("CustomRocketData");
        ControlPos = subTags.getIntArray("ControlPos");
        rocketLogic = new Rocket();
        rocketLogic.readFromNBT(subTags);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        NBTTagCompound tags = new NBTTagCompound();
        rocketLogic.writeToNBT(tags);
        tags.setIntArray("ControlPos", ControlPos);
        nbttagcompound.setTag("CustomRocketData", tags);
    }

    // @Override
    // public void writeToNBT (NBTTagCompound tag)
    // {
    // }

    @Override
    public void onUpdate()
    {
        // if (firstTick && worldObj.isRemote)
        // {
        // firstTick = false;
        // ticksExisted = 0;
        // return;
        // }
        if (ticksExisted > 400) this.kill();
        if (this.posY > this.worldObj.getActualHeight() + 200)
        {
            PacketDispatcher.sendPacketToAllAround(ControlPos[0], ControlPos[1], ControlPos[2], 10, 0, new Packet3Chat(ChatMessageComponent.createFromText("[" + StringUtils.localize("strings.Title") + "] Congratulations! Your rocket sucessfully made it to space!")));
            this.kill();
        }
        if (rocketLogic == null) return;
        if (!worldObj.isRemote)
        {
            rocketLogic.tick();
        }
        this.motionX = rocketLogic.velX;
        this.motionY = rocketLogic.velY;
        this.motionZ = rocketLogic.velZ;
        if (this.motionY < -.5) this.motionY = -.5;// min negative speed is -.5;
        if (this.motionY < 0)
        {
            if (worldObj.getBlockId((int) this.posX, (int) this.posY - 1, (int) this.posZ) != 0)
            {
                // explode
                PacketDispatcher.sendPacketToAllAround(ControlPos[0], ControlPos[1], ControlPos[2], 10, 0, new Packet3Chat(ChatMessageComponent.createFromText("[" + StringUtils.localize("strings.Title") + "] Uh Oh, your rocket hit the ground and blew up!")));
                this.kill();
            }
        }
        setPosition(posX + motionX, posY + motionY, posZ + motionZ);
        // if (worldObj.isRemote)
        // {
        Random rand = new Random();
        for (int i = 0; i < (0 + rocketLogic.Size.ordinal()) * (2 - MSA.proxy.getCurrentParticleSetting()); i++)
        {
            worldObj.spawnParticle("flame", this.posX + rand.nextDouble() * (rand.nextBoolean() ? -1 : 1), this.posY - rand.nextDouble(), this.posZ + rand.nextDouble() * (rand.nextBoolean() ? -1 : 1), rand.nextInt(35) / 100 * (rand.nextBoolean() ? -1 : 1), -rand.nextDouble(), rand.nextInt(35) / 100 * (rand.nextBoolean() ? -1 : 1));
        }
        // }
        // if (worldObj.isRemote)
        // {
        // System.out.println("Rocket entity-Client x:" + posX + " y:" + posY +
        // " z:" + posZ);
        // }
        // else
        // {
        // System.out.println("Rocket entity-Server x:" + posX + " y:" + posY +
        // " z:" + posZ);
        // }
        super.onUpdate();
    }

    @Override
    protected void entityInit()
    {

    }

    @Override
    public void kill()
    {
        super.kill();
        TileEntity control = worldObj.getBlockTileEntity(ControlPos[0], ControlPos[1], ControlPos[2]);
        if (control instanceof LaunchControlLogic)
        {
            ((LaunchControlLogic) control).RocketLogic = null;
            ((LaunchControlLogic) control).forceUpdate();
        }
    }
}
