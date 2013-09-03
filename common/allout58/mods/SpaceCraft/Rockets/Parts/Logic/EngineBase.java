package allout58.mods.SpaceCraft.Rockets.Parts.Logic;

import net.minecraft.nbt.NBTTagCompound;
import allout58.mods.SpaceCraft.SpaceCraftConfig;
import allout58.mods.SpaceCraft.Rockets.RocketEnums;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.FuelType;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.RocketPower;

public abstract class EngineBase extends RocketPart
{
    public FuelType Fuel = FuelType.Solid;
    public int TotalFuel = 0;
    public Boolean isRunning = false;

    public Boolean fire()
    {
        // isRunning = !(rand.nextInt(SpaceCraftConfig.chanceMissFire) == 1);
        isRunning = true;
        return isRunning;
    }

    public int engineTick()
    {
        if (isRunning)
        {
            TotalFuel -= fuelBurnPerTick();
            if(TotalFuel<=0)
            {
                isRunning=false;
                return 0;
            }
            return fuelBurnPerTick() * powerPerFuel();
        }
        else return 0;
    }

    protected abstract int powerPerFuel();

    protected abstract int fuelBurnPerTick();

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        TotalFuel = nbttagcompound.getInteger("TotalFuel");
        isRunning = nbttagcompound.getBoolean("IsRunning");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("Type", (byte) Fuel.ordinal());
        nbttagcompound.setInteger("TotalFuel", TotalFuel);
        nbttagcompound.setBoolean("IsRunning", isRunning);
    }
}
