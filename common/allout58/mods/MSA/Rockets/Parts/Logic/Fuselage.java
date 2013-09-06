package allout58.mods.MSA.Rockets.Parts.Logic;

import java.util.*;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.liquids.LiquidStack;

public class Fuselage extends RocketPart
{
    public ArrayList<AddonBase> Addons = new ArrayList<AddonBase>();
    public Boolean canAcceptLiquidFuel = false;

    public Fuselage()
    {
        Weight = 500;
    }

    public Boolean AddAddon(AddonBase addon)
    {
        // ensure that this new addon is not already in the fuselage
        for (int i = 0; i < Addons.size(); i++)
        {
            if (addon.getClass() == Addons.get(i).getClass())
            {
                return false;
            }
        }
        Addons.add(addon);
        this.Weight += addon.Weight;
        // if this is a liquid fuel tank addon, let others know the fuselage can
        // now accept liquid fuel
        if (addon instanceof LiquidFuelTank)
        {
            canAcceptLiquidFuel = true;
        }
        return true;
    }

    public Boolean AddLiquidFuel(FluidStack fluid)
    {
        if (canAcceptLiquidFuel)
        {
            for (int i = 0; i < Addons.size(); i++)
            {
                if (Addons.get(i) instanceof LiquidFuelTank)
                {
                    return ((LiquidFuelTank) Addons.get(i)).addFuel(fluid);
                }
            }
        }
        return false;
    }
}
