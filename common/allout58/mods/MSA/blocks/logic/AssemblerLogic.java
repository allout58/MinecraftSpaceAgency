package allout58.mods.MSA.blocks.logic;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;
import allout58.mods.MSA.items.ItemList;
import allout58.mods.MSA.rockets.Parts.items.ItemRocketPart;
import allout58.mods.MSA.util.IFacingLogic;

public class AssemblerLogic extends TileEntity implements IFacingLogic, ISidedInventory// IInventory
{
    private byte direction = 0;

    private ItemStack[] inventory;

    public static final int INVENTORY_SIZE = 13;

    public static final int FUEL_INV_LOC = 0;
    public static final int PAYLOAD_INV_LOC = 1;
    public static final int FUELTANK_INV_LOC = 2;
    public static final int FUSELAGE_INV_LOC = 3;
    public static final int[] FIN_INV_LOCS = { 4, 5, 6, 7 };
    public static final int[] ENGINE_INV_LOCS = { 8, 9, 10, 11 };
    public static final int OUTPUT_INV_LOC = 12;

    private boolean needsUpdate = false;

    public AssemblerLogic()
    {
        inventory = new ItemStack[INVENTORY_SIZE];
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

    /* Custom functions */

    public void AssembleRocket()
    {
        if (!canAssemble()) return;
        // Assemble rocket item
        ItemStack rocketIS = new ItemStack(ItemList.rocketFinalized);
        NBTTagCompound tag = new NBTTagCompound();
        rocketIS.stackTagCompound = tag;
        if (inventory[PAYLOAD_INV_LOC] != null)
        {
            NBTTagCompound sub = new NBTTagCompound();
            ((ItemRocketPart) inventory[PAYLOAD_INV_LOC].getItem()).nbtForFinalizedRocket(sub, inventory[PAYLOAD_INV_LOC]);
            tag.setCompoundTag("Payload", sub);
            inventory[PAYLOAD_INV_LOC] = null;
        }
        if (inventory[FUELTANK_INV_LOC] != null)
        {
            NBTTagCompound sub = new NBTTagCompound();
            ((ItemRocketPart) inventory[FUELTANK_INV_LOC].getItem()).nbtForFinalizedRocket(sub, inventory[FUELTANK_INV_LOC]);
            tag.setCompoundTag("FuelTank", sub);
            inventory[FUELTANK_INV_LOC] = null;
        }
        if (inventory[FUSELAGE_INV_LOC] != null)
        {
            NBTTagCompound sub = new NBTTagCompound();
            ((ItemRocketPart) inventory[FUSELAGE_INV_LOC].getItem()).nbtForFinalizedRocket(sub, inventory[FUSELAGE_INV_LOC]);
            tag.setCompoundTag("Fuselage", sub);
            inventory[FUSELAGE_INV_LOC] = null;
        }
        int countEngine = 0;
        for (int i = 0; i < ENGINE_INV_LOCS.length; i++)
        {
            if (inventory[ENGINE_INV_LOCS[i]] != null)
            {
                NBTTagCompound sub = new NBTTagCompound();
                ((ItemRocketPart) inventory[ENGINE_INV_LOCS[i]].getItem()).nbtForFinalizedRocket(sub, inventory[ENGINE_INV_LOCS[i]]);
                tag.setCompoundTag("Engine" + countEngine, sub);
                inventory[ENGINE_INV_LOCS[i]] = null;
                countEngine++;
            }
        }
        tag.setInteger("CountEngines", countEngine);
        int countAddon = 0;
        for (int i = 0; i < FIN_INV_LOCS.length; i++)
        {
            if (inventory[FIN_INV_LOCS[i]] != null)
            {
                NBTTagCompound sub = new NBTTagCompound();
                ((ItemRocketPart) inventory[FIN_INV_LOCS[i]].getItem()).nbtForFinalizedRocket(sub, inventory[FIN_INV_LOCS[i]]);
                tag.setCompoundTag("Addon" + countAddon, sub);
                inventory[FIN_INV_LOCS[i]] = null;
                countAddon++;
            }
        }
        tag.setInteger("CountAddons", countAddon);
        inventory[OUTPUT_INV_LOC] = rocketIS;
    }

    public boolean canAssemble()
    {
        if (inventory[PAYLOAD_INV_LOC] == null) return false;
        if (inventory[FUSELAGE_INV_LOC] == null) return false;
        boolean validRocket = false;
        for (int i = 0; i < ENGINE_INV_LOCS.length; i++)
        {
            validRocket = validRocket || (inventory[ENGINE_INV_LOCS[i]] != null);
        }
        if (!validRocket) return false;
        return true;
    }

    /* NBT */

    @Override
    public void readFromNBT(NBTTagCompound tags)
    {
        super.readFromNBT(tags);
        direction = tags.getByte("Direction");

        NBTTagList tagList = tags.getTagList("Items");
        inventory = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < inventory.length)
            {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tags)
    {
        super.writeToNBT(tags);
        tags.setByte("Direction", direction);

        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
        {
            if (inventory[currentIndex] != null)
            {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);
                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        tags.setTag("Items", tagList);
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
        if (inventory[FUEL_INV_LOC] != null)// will be actuall burn and btn in
                                            // future
        {
            AssembleRocket();
            inventory[FUEL_INV_LOC] = null;
        }
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
        return "container.RocketAssembler";
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
        return 64;
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

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        // TODO Auto-generated method stub
        return new int[] { FUEL_INV_LOC };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, int side)
    {
        if (!TileEntityFurnace.isItemFuel(itemstack)) return false;
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, int side)
    {
        return false;
    }
}
