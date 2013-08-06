package allout58.mods.SpaceCraft.WorldGen;

import java.util.Random;

import allout58.mods.SpaceCraft.SpaceCraft;
import allout58.mods.SpaceCraft.SpaceCraftConfig;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class GenStarSteelOre implements IWorldGenerator
{

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        // based almost entirely on MFFS Monazite World Generation code
        chunkX = chunkX << 4;
        chunkZ = chunkZ << 4;

        WorldGenMinable worldGenMinable = new WorldGenMinable(SpaceCraft.oreStarSteel.blockID, 0, SpaceCraft.starSteelGenAmount + 1, Block.stone.blockID);

        for (int i = 0; i < SpaceCraftConfig.starsteelgenRarity; i++)
        {
            int x = chunkX + random.nextInt(16);
            int y = random.nextBoolean() ? random.nextInt(SpaceCraftConfig.starsteelgenMaxY_Lower - SpaceCraftConfig.starsteelgenMinY_Lower) + SpaceCraftConfig.starsteelgenMinY_Lower : random.nextInt(SpaceCraftConfig.starsteelgenMaxY_Upper - SpaceCraftConfig.starsteelgenMinY_Upper) + SpaceCraftConfig.starsteelgenMinY_Upper;// either
                                                                                                                                                                                                                                                                                                                                     // lower
                                                                                                                                                                                                                                                                                                                                     // band
                                                                                                                                                                                                                                                                                                                                     // Y-vals
                                                                                                                                                                                                                                                                                                                                     // or
                                                                                                                                                                                                                                                                                                                                     // upper
                                                                                                                                                                                                                                                                                                                                     // band
                                                                                                                                                                                                                                                                                                                                     // Y-vals
            int z = chunkZ + random.nextInt(16);

            int randAmount = random.nextInt(SpaceCraftConfig.starsteelgenAmount + 1);
            // TODO: Why is randAmount not used?
            worldGenMinable.generate(world, random, x, y, z);
        }
    }
}
