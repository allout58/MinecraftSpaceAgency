package allout58.mods.SpaceCraft.Rockets.Parts.Logic;

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
        isRunning = !(rand.nextInt(SpaceCraftConfig.chanceMissFire) == 1);
        return isRunning;
    }

    public int engineTick()
    {
        if (isRunning)
        {
            TotalFuel -= fuelBurnPerTick();
            return fuelBurnPerTick() * powerPerFuel();
        }
        else return 0;
    }

    protected abstract int powerPerFuel();

    protected abstract int fuelBurnPerTick();
}
