package allout58.mods.MSA.Blocks;

import allout58.mods.MSA.MSA;
import allout58.mods.MSA.constants.MSATextures;
import allout58.mods.MSA.util.StringUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockOreStarSteel extends Block
{

    public BlockOreStarSteel(int par1, Material par2Material)
    {
        super(par1, par2Material);
        setLightValue(0.65F);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setUnlocalizedName("oreStarSteel");
        setCreativeTab(MSA.creativeTab);
        setTextureName(MSATextures.RESOURCE_CONTEXT + ":" + this.getUnlocalizedName().substring(5));
    }
}
