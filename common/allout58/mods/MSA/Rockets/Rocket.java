package allout58.mods.MSA.Rockets;

import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.EntityFlying;
import net.minecraft.nbt.NBTTagCompound;
import allout58.mods.MSA.Rockets.Parts.Logic.EngineBase;
import allout58.mods.MSA.Rockets.Parts.Logic.Fuselage;
import allout58.mods.MSA.Rockets.Parts.Logic.PayloadBase;
import allout58.mods.MSA.Rockets.Parts.Logic.SolidFueledEngine;
import allout58.mods.MSA.Rockets.RocketEnums.*;
import allout58.mods.MSA.util.INBTTagable;

public class Rocket implements INBTTagable
{
    public static final double accGrav = -9.81;

    private EngineBase[] Engines;
    private Fuselage Fuselage;
    private PayloadBase Payload;
    public int totalWeight = 0;
    public double velX = 0;
    public double velY = 0;
    public double velZ = 0;
    public RocketSize Size;

    public Rocket()
    {
        Size = RocketSize.Small;
    }

    public Rocket(EngineBase[] engines, Fuselage fuselage, PayloadBase payload)
    {
        Engines = engines;
        Fuselage = fuselage;
        Payload = payload;
        this.Initialize();
    }

    public void Initialize()
    {
        Size = Fuselage.Size;
        for (int i = 0; i < Engines.length; i++)
        {
            totalWeight += Engines[i].Weight;
        }
        totalWeight += Fuselage.Weight;
        totalWeight += Payload.Weight;
    }

    public void Launch()
    {
        for (int i = 0; i < Engines.length; i++)
        {
            Engines[i].fire();
        }
    }

    public void tick()
    {
        int power = 0;
        for (int i = 0; i < Engines.length; i++)
        {
            power += Engines[i].engineTick();
        }
        if (power <= 0)
        {
            System.out.println("ROCKET OUT OF FUEL!");
        }
        velY = (double) ((power - totalWeight) / 120.1);
        System.out.println("Rocket velY: "+velY);
        velX = 0;
        velZ = 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        Size = RocketSize.values()[nbttagcompound.getByte("Size")];
        int EngLen = nbttagcompound.getByte("NumEngines");
        Engines = new EngineBase[EngLen];
        for (int i = 0; i < EngLen; i++)
        {
            NBTTagCompound tags = nbttagcompound.getCompoundTag("Engine" + i);
            FuelType type = FuelType.values()[tags.getByte("Type")];
            RocketSize partSize = RocketSize.values()[tags.getByte("PartSize")];
            switch (type)
            {
                case Liquid:
                    FMLLog.getLogger().warning("Liquid fueled rocket engines have not been implemented. Something bad is probably about to happen.");
                    break;
                case Solid:
                    Engines[i] = new SolidFueledEngine(partSize);
                    break;
                default:
                    FMLLog.getLogger().log(Level.SEVERE, "Rocket unable to read engine type properly.");
                    break;

            }
            if (Engines[i] != null) Engines[i].readFromNBT(tags);
        }
        NBTTagCompound fuselageTags = nbttagcompound.getCompoundTag("Fuselage");
        Fuselage = new Fuselage();
        Fuselage.readFromNBT(fuselageTags);
        NBTTagCompound payloadTags = nbttagcompound.getCompoundTag("Payload");
        // /TODO Payload section of NBT read/write
        //Payload = new Payl
        // Payload.readFromNBT(payloadTags);
        this.Initialize();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("Size", (byte) Size.ordinal());
        nbttagcompound.setByte("NumEngines", (byte) Engines.length);
        for (int i = 0; i < Engines.length; i++)
        {
            NBTTagCompound engineTags = new NBTTagCompound();
            Engines[i].writeToNBT(engineTags);
            nbttagcompound.setCompoundTag("Engine" + i, engineTags);
        }
        NBTTagCompound fuselageTag = new NBTTagCompound();
        Fuselage.writeToNBT(fuselageTag);
        nbttagcompound.setCompoundTag("Fuselage", fuselageTag);
        // payload stuff
    }

}
