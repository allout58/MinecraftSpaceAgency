package allout58.mods.SpaceCraft.Blocks;

import allout58.mods.SpaceCraft.SpaceCraft;
import allout58.mods.SpaceCraft.util.StringUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockStorageStarSteel extends Block
{

    public BlockStorageStarSteel(int par1, Material par2Material)
    {
        super(par1, par2Material);
        setLightValue(0.2F);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setUnlocalizedName("blockStarSteel");
        setCreativeTab(SpaceCraft.creativeTab);
        func_111022_d("spacecraft:" + this.getUnlocalizedName().substring(5));
    }
}
