package allout58.mods.SpaceCraft.Rockets.Parts.Logic;

import net.minecraftforge.fluids.FluidStack;
import allout58.mods.SpaceCraft.SpaceCraft;
import allout58.mods.SpaceCraft.Rockets.RocketEnums;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.RocketSize;

public class LiquidFuelTank extends AddonBase
{
    public int FuelStored = 0;
    public final int StorageSize;

    public LiquidFuelTank(RocketSize size)
    {
        this.Size = size;
        this.Description = "Stores liquid fuel for use in liquid-fueled engine.";
        switch (size)
        {
            case Small:
                this.Weight = 80;
                this.StorageSize = 8000;
                break;
            case Medium:
                this.Weight = 100;
                this.StorageSize = 16000;
                break;
            case Large:
                this.Weight = 120;
                this.StorageSize = 32000;
                break;
            default:
                this.Weight = -1;
                this.StorageSize = 0;
                break;

        }
        this.Name = size.toString() + " Liquid Fuel Tank";
    }

    public Boolean addFuel(FluidStack fuel)
    {
        if (FuelStored + fuel.amount <= StorageSize)
        {
            // check if valid liquid fuel
            FuelStored += fuel.amount;
            return true;
        }
        else return false;
    }
}
