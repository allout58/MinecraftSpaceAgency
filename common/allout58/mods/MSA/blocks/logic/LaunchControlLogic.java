package allout58.mods.MSA.blocks.logic;

// ~--- non-JDK imports --------------------------------------------------------

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;
import allout58.mods.MSA.blocks.BlockList;
import allout58.mods.MSA.rockets.Rocket;
import allout58.mods.MSA.rockets.RocketEnums.RocketSize;
import allout58.mods.MSA.rockets.Parts.logic.EngineBase;
import allout58.mods.MSA.rockets.Parts.logic.Fin;
import allout58.mods.MSA.rockets.Parts.logic.Fuselage;
import allout58.mods.MSA.rockets.Parts.logic.PayloadBase;
import allout58.mods.MSA.rockets.Parts.logic.PayloadSatellite;
import allout58.mods.MSA.rockets.Parts.logic.SolidFueledEngine;
import allout58.mods.MSA.rockets.entity.EntityRocket;
import allout58.mods.MSA.util.IFacingLogic;
import allout58.mods.MSA.util.StringUtils;
import cpw.mods.fml.common.network.PacketDispatcher;

public class LaunchControlLogic extends TileEntity implements IFacingLogic, ILinkable, IInventory
{
    private static final int[] sizes = new int[] { 3, 5, 7, 8 };// last size not
                                                                // used, extra
    private ItemStack[] inventory;

    public static final int INVENTORY_SIZE = 1;

    public static final int ROCKET_INV_LOC = 0;

    public boolean isValidStructure = false;

    private byte direction = 0;
    private byte size = 0;
    private int height = 0;
    private int flameOutLength = 0;
    private int centerX = 0;
    private int centerY = 0;
    private int centerZ = 0;

    private EntityPlayer launchedBy;

    public Rocket RocketLogic;

    private boolean needsUpdate = false;

    public LaunchControlLogic()
    {
        inventory = new ItemStack[INVENTORY_SIZE];
    }

    /* Multiblock Structure Checking */

    public boolean checkValidStructure(int x, int y, int z)
    {
        flameOutLength = height = size = 0;
        isValidStructure = checkTowerHeight(x, y, z);
        isValidStructure &= checkLaunchPad(x, y, z);
        isValidStructure &= checkFlameOutlets(x, y, z);

        needsUpdate = true;

        return isValidStructure;
    }

    private boolean checkFlameOutlets(int x, int y, int z)
    {
        boolean isGood = false;
        int halfSide = (int) Math.floor(sizes[size] / 2);
        long[] lengths = new long[] { 0, 0, 0 };
        switch (direction)
        {
            case 2:
                lengths[0] = flamePathLength(centerX - halfSide, centerY, centerZ, ForgeDirection.WEST);
                lengths[1] = flamePathLength(centerX, centerY, centerZ + halfSide, ForgeDirection.SOUTH);
                lengths[2] = flamePathLength(centerX + halfSide, centerY, centerZ, ForgeDirection.EAST);
                break;
            case 3:
                lengths[0] = flamePathLength(centerX - halfSide, centerY, centerZ, ForgeDirection.WEST);
                lengths[1] = flamePathLength(centerX, centerY, centerZ - halfSide, ForgeDirection.NORTH);
                lengths[2] = flamePathLength(centerX + halfSide, centerY, centerZ, ForgeDirection.EAST);
                break;
            case 4:
                lengths[0] = flamePathLength(centerX, centerY, centerZ - halfSide, ForgeDirection.NORTH);
                lengths[1] = flamePathLength(centerX, centerY, centerZ + halfSide, ForgeDirection.SOUTH);
                lengths[2] = flamePathLength(centerX + halfSide, centerY, centerZ, ForgeDirection.EAST);
                break;
            case 5:
                lengths[0] = flamePathLength(centerX - halfSide, centerY, centerZ, ForgeDirection.WEST);
                lengths[1] = flamePathLength(centerX, centerY, centerZ + halfSide, ForgeDirection.SOUTH);
                lengths[2] = flamePathLength(centerX, centerY, centerZ - halfSide, ForgeDirection.NORTH);
                break;
        }
        flameOutLength = (int) MathHelper.average(lengths);
        if (flameOutLength >= 1) isGood = true;
        return isGood;
    }

    private int flamePathLength(int x, int y, int z, ForgeDirection dir)
    {
        int length = 0;
        switch (dir)
        {
            case NORTH:
                while (worldObj.getBlockId(x, y, --z) == Block.stoneSingleSlab.blockID)
                    length++;
                break;
            case SOUTH:
                while (worldObj.getBlockId(x, y, ++z) == Block.stoneSingleSlab.blockID)
                    length++;
                break;
            case EAST:
                while (worldObj.getBlockId(++x, y, z) == Block.stoneSingleSlab.blockID)
                    length++;
                break;
            case WEST:
                while (worldObj.getBlockId(--x, y, z) == Block.stoneSingleSlab.blockID)
                    length++;
                break;
            default:
                return -1;
        }
        return length;
    }

    private boolean checkLaunchPad(int x, int y, int z)
    {
        for (int i = 0; i < sizes.length; i++)
        {
            boolean isGood = true;
            int halfSide = (int) Math.floor(sizes[i] / 2);
            switch (direction)
            {
                case 2:
                    for (int x1 = x - halfSide; x1 <= x + halfSide && isGood; x1++)
                    {
                        for (int z1 = z + 1; z1 <= z + sizes[i] && isGood; z1++)
                        {
                            // worldObj.setBlock(x1, y, z1, Block.fire.blockID);
                            if (this.worldObj.getBlockId(x1, y - 1, z1) != BlockList.ssBuilding.blockID)
                            {
                                isGood = false;
                            }
                        }
                    }
                    centerX = x;
                    centerZ = z + halfSide;
                    break;
                case 3:
                    for (int x1 = x - halfSide; x1 <= x + halfSide && isGood; x1++)
                    {
                        for (int z1 = z - sizes[i]; z1 <= z - 1 && isGood; z1++)
                        {
                            // worldObj.setBlock(x1, y, z1, Block.fire.blockID);
                            if (this.worldObj.getBlockId(x1, y - 1, z1) != BlockList.ssBuilding.blockID)
                            {
                                isGood = false;
                            }
                        }
                    }
                    centerX = x;
                    centerZ = z - halfSide;
                    break;
                case 4:
                    for (int x1 = x + 1; x1 <= x + sizes[i] && isGood; x1++)
                    {
                        for (int z1 = z - halfSide; z1 <= z + halfSide && isGood; z1++)
                        {
                            // worldObj.setBlock(x1, y, z1, Block.fire.blockID);
                            if (this.worldObj.getBlockId(x1, y - 1, z1) != BlockList.ssBuilding.blockID)
                            {
                                isGood = false;
                            }
                        }
                    }
                    centerX = x + halfSide;
                    centerZ = z;
                    break;

                case 5:
                    for (int x1 = x - sizes[i]; x1 <= x - 1 && isGood; x1++)
                    {
                        for (int z1 = z - halfSide; z1 <= z + halfSide && isGood; z1++)
                        {
                            // worldObj.setBlock(x1, y+1, z1,
                            // Block.fire.blockID);
                            if (this.worldObj.getBlockId(x1, y - 1, z1) != BlockList.ssBuilding.blockID)
                            {
                                isGood = false;
                            }
                        }
                    }
                    centerX = x - halfSide;
                    centerZ = z;
                    break;
            }
            centerY = y - 1;
            if (isGood)
            {
                this.size = (byte) i;
            }
            else if (size >= 0)
            {
                return true;
            }
            else return false;
        }
        return false;
    }

    private boolean checkTowerHeight(int x, int y, int z)
    {
        while (worldObj.getBlockId(x, ++y, z) == BlockList.launchTower.blockID)
        {
            height++;
        }
        return (height > 0);
    }

    /* Launching */

    public void LaunchSequence()
    {
        if (!isValidStructure) return;
        if (RocketLogic != null)
        {
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 10, 0, new Packet3Chat(ChatMessageComponent.createFromText("[" + StringUtils.localize("strings.Title") + "] Rocket already launched and not yet gone, please wait.")));
            return;
        }
        NBTTagCompound tag = inventory[ROCKET_INV_LOC].stackTagCompound;
        EngineBase[] engines = new SolidFueledEngine[tag.getInteger("CountEngines")];
        for (int i = 0; i < tag.getInteger("CountEngines"); i++)
        {
            engines[i] = new SolidFueledEngine(RocketSize.values()[tag.getCompoundTag("Engine" + i).getInteger("Size")]);
        }
        Fuselage fuselage = new Fuselage();
        for (int i = 0; i < tag.getInteger("CountAddons"); i++)
        {
            fuselage.AddAddon(new Fin(RocketSize.values()[tag.getCompoundTag("Addon" + i).getInteger("Size")]));
        }
        PayloadBase payload = new PayloadSatellite();// @#^#%@#^
        RocketLogic = new Rocket(engines, fuselage, payload);
        System.out.println("Launching " + RocketLogic.Size.toString() + " size rocket...");
        if (RocketLogic.Size.ordinal() > this.size)
        {
            // explodeLaunchPad();
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 10, 0, new Packet3Chat(ChatMessageComponent.createFromText("[" + StringUtils.localize("strings.Title") + "] The rocket is too big! It's going to blow!")));
            RocketLogic = null;
            // failLaunch();
            return;
        }
        if ((((RocketLogic.Size.ordinal() + 1) ^ 3) * 2) > height)
        {
            // setRocketAccuracy(-.005*size.ordinal()^3*2-height);
            System.out.println(((RocketLogic.Size.ordinal() + 1) ^ 3) * 2);
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 10, 0, new Packet3Chat(ChatMessageComponent.createFromText("[" + StringUtils.localize("strings.Title") + "] Launch tower not high enough. The rocket looses accuracy.")));
        }
        if ((((RocketLogic.Size.ordinal() + 1) ^ 3) * 3) > flameOutLength)
        {
            // flamesAroundEnd()
            System.out.println(((RocketLogic.Size.ordinal() + 1) ^ 3) * 3);
            PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 10, 0, new Packet3Chat(ChatMessageComponent.createFromText("[" + StringUtils.localize("strings.Title") + "] Flame outlets not long enough. Watch out for flames around the ends!")));
        }
        if (!worldObj.isRemote) worldObj.spawnEntityInWorld(new EntityRocket(worldObj, RocketLogic, centerX + 0.5, centerY + 3.5, centerZ + 0.5, this.xCoord, this.yCoord, this.zCoord));
    }

    /* Utilities */
    public void forceUpdate()
    {
        needsUpdate = true;
    }

    /* IFacingLogic */
    @Override
    public byte getRenderDirection()
    {
        return direction;
    }

    @Override
    public ForgeDirection getForgeDirection()
    {
        return ForgeDirection.VALID_DIRECTIONS[direction];
    }

    @Override
    public void setDirection(int side)
    {
    }

    @Override
    public void setDirection(float yaw, float pitch, EntityLivingBase player)
    {
        int facing = MathHelper.floor_double((double) (yaw / 360) + 0.5D) & 3;

        switch (facing)
        {
            case 0:
                direction = 2;

                break;

            case 1:
                direction = 5;

                break;

            case 2:
                direction = 3;

                break;

            case 3:
                direction = 4;

                break;
        }
    }

    /* NBT */

    @Override
    public void readFromNBT(NBTTagCompound tags)
    {
        super.readFromNBT(tags);
        direction = tags.getByte("Direction");
        isValidStructure = tags.getBoolean("IsValidStructure");
        if (tags.hasKey("RocketLogic"))
        {
            RocketLogic = new Rocket();
            RocketLogic.readFromNBT(tags.getCompoundTag("RocketLogic"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tags)
    {
        super.writeToNBT(tags);
        tags.setByte("Direction", direction);
        tags.setBoolean("IsValidStructure", isValidStructure);
        if (RocketLogic != null)
        {
            NBTTagCompound rocketTag = new NBTTagCompound();
            RocketLogic.writeToNBT(rocketTag);
            tags.setCompoundTag("RocketLogic", rocketTag);
        }
    }

    /* Packets */
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet)
    {
        readFromNBT(packet.data);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void updateEntity()
    {
        if (needsUpdate)
        {
            needsUpdate = false;
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public ItemStack Link(ItemStack linkCard)
    {
        linkCard.stackTagCompound = new NBTTagCompound();
        linkCard.stackTagCompound.setInteger("targetX", xCoord);
        linkCard.stackTagCompound.setInteger("targetY", yCoord);
        linkCard.stackTagCompound.setInteger("targetZ", zCoord);
        linkCard.getItem().setDamage(linkCard, 1);
        // if(worldObj.isRemote)
        // {
        // FMLClientHandler.instance().getClient().playerController.addChatMessage("Link Established on "
        // + (worldObj.isRemote ? "client" : "server"));
        // }
        return linkCard;
    }

    /* Inventory stuff */

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        ItemStack itemStack = getStackInSlot(slot);
        if (itemStack != null)
        {
            if (itemStack.stackSize <= amount)
            {
                setInventorySlotContents(slot, null);
            }
            else
            {
                itemStack = itemStack.splitStack(amount);
                if (itemStack.stackSize == 0)
                {
                    setInventorySlotContents(slot, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack itemStack = getStackInSlot(slot);
        if (itemStack != null)
        {
            setInventorySlotContents(slot, null);
        }
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack)
    {
        inventory[slot] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
        {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName()
    {
        // TODO Possibly add custom names
        return "container.LaunchControl";
    }

    @Override
    public boolean isInvNameLocalized()
    {
        // TODO This needs to change w/ custom names
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public void openChest()
    {
    }

    @Override
    public void closeChest()
    {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack)
    {
        // TODO Actually set this up to only let in appropriate parts
        return true;
    }
}