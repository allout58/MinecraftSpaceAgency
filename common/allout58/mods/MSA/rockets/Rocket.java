package allout58.mods.MSA.rockets;

import java.util.logging.Level;

import net.minecraft.nbt.NBTTagCompound;
import allout58.mods.MSA.rockets.RocketEnums.FuelType;
import allout58.mods.MSA.rockets.RocketEnums.RocketSize;
import allout58.mods.MSA.rockets.Parts.logic.EngineBase;
import allout58.mods.MSA.rockets.Parts.logic.Fuselage;
import allout58.mods.MSA.rockets.Parts.logic.PayloadBase;
import allout58.mods.MSA.rockets.Parts.logic.PayloadSatellite;
import allout58.mods.MSA.rockets.Parts.logic.SolidFueledEngine;
import allout58.mods.MSA.util.INBTTagable;
import cpw.mods.fml.common.FMLLog;

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
    public double currentPower = 0;
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
        checkWeight();
    }

    public int checkWeight()
    {
        totalWeight = 0;
        for (int i = 0; i < Engines.length; i++)
        {
            totalWeight += Engines[i].Weight;
        }
        totalWeight += Fuselage.Weight;
        totalWeight += Payload.Weight;
        return totalWeight;
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
        checkWeight();
        int power = 0;
        for (int i = 0; i < Engines.length; i++)
        {
            power += Engines[i].engineTick();
        }
        if (power <= 0)
        {
            System.out.println("ROCKET OUT OF FUEL!");
        }
        currentPower = power;
        velY = (double) ((power - totalWeight) / 120.1);
        velX = 0;
        velZ = 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        velX = nbttagcompound.getDouble("velX");
        velY = nbttagcompound.getDouble("velY");
        velZ = nbttagcompound.getDouble("velZ");
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
        switch (payloadTags.getInteger("Type"))
        {
            case 0:
                Payload = new PayloadSatellite();
                break;
            default:
                FMLLog.severe("Payload type unknown! Unable to load payload.");
                break;
        }
        if (Payload != null) Payload.readFromNBT(payloadTags);
        this.Initialize();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setDouble("velX", velX);
        nbttagcompound.setDouble("velY", velY);
        nbttagcompound.setDouble("velZ", velZ);
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
        NBTTagCompound payloadTag = new NBTTagCompound();
        Payload.writeToNBT(payloadTag);
        nbttagcompound.setCompoundTag("Payload", payloadTag);
    }
}
