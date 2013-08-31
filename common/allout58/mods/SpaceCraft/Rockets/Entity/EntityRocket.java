package allout58.mods.SpaceCraft.Rockets.Entity;

import java.util.Random;

import allout58.mods.SpaceCraft.Rockets.Rocket;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.RocketSize;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.world.World;

public class EntityRocket extends Entity
{
    Rocket rocketLogic;

    public EntityRocket(World world)
    {
        super(world);
        this.preventEntitySpawning = true;
    }

    public EntityRocket(World par1World, Rocket rLogic, double x, double y, double z)
    {
        this(par1World);
        rocketLogic = rLogic;
        this.setPosition(x, y, z);
        rocketLogic.Launch();
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        // super.readFromNBT(nbttagcompound);
        rocketLogic = new Rocket();
        rocketLogic.readFromNBT(nbttagcompound);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        // super.writeToNBT(nbttagcompound);
        rocketLogic.writeToNBT(nbttagcompound);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if (ticksExisted > 500) this.kill();
        if (rocketLogic == null) return;
        if (!worldObj.isRemote)
        {
            rocketLogic.tick();
            this.motionX = rocketLogic.velX;
            this.motionY = rocketLogic.velY;
            this.motionZ = rocketLogic.velZ;
        }
        setPosition(posX + motionX, posY + motionY, posZ + motionZ);
        if (worldObj.isRemote)
        {
            Random rand = new Random();
            for (int i = 0; i < (rocketLogic.Size.ordinal() + 1) * 2; i++)
            {
                worldObj.spawnParticle("flame", this.posX + rand.nextDouble() * (rand.nextBoolean() ? -1 : 1), this.posY - rand.nextDouble(), this.posZ + rand.nextDouble() * (rand.nextBoolean() ? -1 : 1), rand.nextInt(35) / 100 * (rand.nextBoolean() ? -1 : 1), -rand.nextDouble(), rand.nextInt(35) / 100 * (rand.nextBoolean() ? -1 : 1));
            }
        }
    }

    @Override
    protected void entityInit()
    {

    }
}
