package allout58.mods.MSA.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import allout58.mods.MSA.MSA;
import allout58.mods.MSA.constants.MSATextures;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class BlockSSBuilding extends Block {
	public Icon side, top;

	public BlockSSBuilding(int par1, Material par2Material) {
		super(par1, par2Material);
		setHardness(2.0F);
		setStepSound(soundStoneFootstep);
		setUnlocalizedName("ssBuilding");
		setCreativeTab(MSA.creativeTab);
		setTextureName(MSATextures.RESOURCE_CONTEXT + ":"
				+ this.getUnlocalizedName().substring(5) + "_side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		if (side == 1 || side == 0)
			return this.top;
		else
			return this.side;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister ir) {
		this.side = ir.registerIcon(MSATextures.RESOURCE_CONTEXT + ":"
				+ this.getUnlocalizedName().substring(5) + "_side");
		this.top = ir.registerIcon(MSATextures.RESOURCE_CONTEXT + ":"
				+ this.getUnlocalizedName().substring(5) + "_bottom");
	}
}
