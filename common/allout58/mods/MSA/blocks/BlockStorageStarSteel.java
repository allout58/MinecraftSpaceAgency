package allout58.mods.MSA.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import allout58.mods.MSA.MSA;
import allout58.mods.MSA.constants.MSATextures;

public class BlockStorageStarSteel extends Block
{

    public BlockStorageStarSteel(int par1, Material par2Material)
    {
        super(par1, par2Material);
        setLightValue(0.2F);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setUnlocalizedName("blockStarSteel");
        setCreativeTab(MSA.creativeTab);
        setTextureName(MSATextures.RESOURCE_CONTEXT + ":" + this.getUnlocalizedName().substring(5));
    }
}
