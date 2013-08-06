package allout58.mods.SpaceCraft.WorldGen;

// ~--- non-JDK imports --------------------------------------------------------

import allout58.mods.SpaceCraft.SpaceCraft;

import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;

// ~--- JDK imports ------------------------------------------------------------

import java.util.HashSet;
import java.util.Random;

public class GenMeteors implements IWorldGenerator
{
    final private int constWidth = 16;

    @Override
    public void generate(Random random, int chX, int chZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        int chunkX = chX * 16 + 8, chunkZ = chZ * 16 + 8;
        if (random.nextInt(50) == 0)
        {
            int i = chunkX + random.nextInt(16);
            int j = random.nextInt(64) + 644;
            int k = chunkZ + random.nextInt(16);
            int height = findGround(world, i, j, k);

            if (height != -1)
            {

                int type = random.nextInt(10);

                if (type == 9)
                {
                    generateLargeMeteor(i, height, k, world);
                }
                else if ((type < 9) && (type > 5))
                {
                    generateMediumMeteor(i, height, k, world);
                }
                else
                {
                    generateSmallMeteor(i, height, k, world);
                }
            }
        }
    }

    // based on Natura's tree gen
    int findGround(World world, int x, int y, int z)
    {
        int returnHeight = -1;
        int blockID = world.getBlockId(x, y - 1, z);

        if (!Block.opaqueCubeLookup[world.getBlockId(x, y, z)] && ((blockID == Block.dirt.blockID) || (blockID == Block.grass.blockID) || (blockID == Block.stone.blockID)))
        {
            return y;
        }

        int height = 90;

        do
        {
            int j1 = world.getBlockId(x, height, z);

            if ((j1 == Block.dirt.blockID) || (j1 == Block.grass.blockID) || (j1 == Block.stone.blockID))
            {
                if (!Block.opaqueCubeLookup[world.getBlockId(x, height + 1, z)])
                {
                    returnHeight = height + 1;
                }

                break;
            }

            height--;
        } while (height > 0);

        return returnHeight;
    }

    int generateCrater(double radius, int x, int y, int z, World world)
    {

        // delete all blocks in radius 'radius' of (x,y,z)
        for (double x1 = x - radius; x1 < x + radius; x1++)
        {
            for (double y1 = y - radius; y1 < y + radius; y1++)
            {
                for (double z1 = z - radius; z1 < z + radius; z1++)
                {
                    double dist = Math.floor(Math.pow(x - x1, 2) + Math.pow(y - y1, 2) + Math.pow(z - z1, 2));

                    if (dist <= Math.pow(radius, 2))
                    {
                        world.destroyBlock((int) x1, (int) y1, (int) z1, false);
                        if (world.rand.nextInt(2) == 0) world.setBlock((int) x1, (int) y1, (int) z1, BlockFire.fire.blockID);
                    }
                }
            }
        }
        // lowest height of the crater
        return (int) (y - radius);
    }

    void generateSmallMeteor(int x, int y, int z, World world)
    {
        int newY = generateCrater(2, x, y, z, world);

        world.setBlock(x, newY, z, SpaceCraft.oreStarSteel.blockID);
        System.out.println("Small Meteor at " + x + "," + z);
    }

    void generateMediumMeteor(int x, int y, int z, World world)
    {
        int newY = generateCrater(4.5, x, y, z, world);

        for (int x1 = x - 1; x1 < x + 1; x1++)
        {
            for (int y1 = newY; y1 < newY + 2; y1++)
            {
                for (int z1 = z - 1; z1 < z + 1; z1++)
                {
                    world.setBlock(x1, y1, z1, SpaceCraft.oreStarSteel.blockID);
                }
            }
        }

        System.out.println("Medium Meteor at " + x + "," + z);
    }

    void generateLargeMeteor(int x, int y, int z, World world)
    {
        int newY = generateCrater(9.5, x, y, z, world);

        for (int x1 = x - 2; x1 < x + 2; x1++)
        {
            for (int y1 = newY; y1 < newY + 4; y1++)
            {
                for (int z1 = z - 2; z1 < z + 2; z1++)
                {
                    world.setBlock(x1, y1 + 1, z1, SpaceCraft.oreStarSteel.blockID);
                }
            }
        }
        System.out.println("Large Meteor at " + x + "," + z);
    }
}

// ~ Formatted by Jindent --- http://www.jindent.com
