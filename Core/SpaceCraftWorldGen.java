package allout58.mods.SpaceCraft.Core;

import java.util.Random;

import allout58.mods.SpaceCraft.SpaceCraft;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class SpaceCraftWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		//based almost entirely on MFFS World Gen code
		chunkX = chunkX<<4;
		chunkZ = chunkZ<<4;
		
		WorldGenMinable worldGenMinable = new WorldGenMinable(SpaceCraft.oreStarSteel.blockID, 0, SpaceCraft.starSteelGenAmount + 1, Block.stone.blockID);
		
        for (int i = 0; i < SpaceCraft.starSteelGenAmount + 1; i++)
        {
            int x = chunkX + random.nextInt(16);
            int y = random.nextInt(80) + 0;
            int z = chunkZ + random.nextInt(16);

            int randAmount = random.nextInt(SpaceCraft.starSteelGenAmount + 1);

            worldGenMinable.generate(world, random, x, y, z);
        }
	}
}
