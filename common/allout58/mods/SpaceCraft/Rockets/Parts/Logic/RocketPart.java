package allout58.mods.SpaceCraft.Rockets.Parts.Logic;

import java.util.Random;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

import allout58.mods.SpaceCraft.Rockets.RocketEnums;
import allout58.mods.SpaceCraft.Rockets.RocketEnums.RocketSize;

public abstract class RocketPart
{
    public int Weight=0;
    protected Random rand=new Random();
    public RocketSize Size=RocketSize.Small;
    public String Name="";
    public String Description="";
    
}
