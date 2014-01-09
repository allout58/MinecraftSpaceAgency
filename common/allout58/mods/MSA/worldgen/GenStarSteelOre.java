package allout58.mods.MSA.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import allout58.mods.MSA.MSAConfig;
import allout58.mods.MSA.blocks.BlockList;
import cpw.mods.fml.common.IWorldGenerator;

public class GenStarSteelOre implements IWorldGenerator
{

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        // based almost entirely on MFFS Monazite World Generation code
        chunkX = chunkX << 4;
        chunkZ = chunkZ << 4;

        WorldGenMinable worldGenMinable = new WorldGenMinable(BlockList.oreStarSteel.blockID, 0, MSAConfig.starsteelgenAmount + 1, Block.stone.blockID);

        for (int i = 0; i < MSAConfig.starsteelgenRarity; i++)
        {
            int x = chunkX + random.nextInt(16);
            int y = random.nextBoolean() ? random.nextInt(MSAConfig.starsteelgenMaxY_Lower - MSAConfig.starsteelgenMinY_Lower) + MSAConfig.starsteelgenMinY_Lower : random.nextInt(MSAConfig.starsteelgenMaxY_Upper - MSAConfig.starsteelgenMinY_Upper) + MSAConfig.starsteelgenMinY_Upper;// either
                                                                                                                                                                                                                                                                                           // //
                                                                                                                                                                                                                                                                                           // Y-vals
            int z = chunkZ + random.nextInt(16);

            int randAmount = random.nextInt(MSAConfig.starsteelgenAmount + 1);
            // TODO: Why is randAmount not used?
            worldGenMinable.generate(world, random, x, y, z);
        }
    }
}
