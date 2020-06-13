package com.uwucraft.website;

import org.bukkit.inventory.ItemStack;

public class SerializeItem {

	public String test;
	public String Itemstring(ItemStack[] inventory)
	{
		for (int i = 1; i < inventory.length; i++) {
			//Bukkit.getLogger().info("Item Lists " + this.inventory[i]);
			this.test = this.test + inventory[i];
		}
		return this.test;
	}
	 public ItemStack[] cloneStacks(ItemStack[] stacks) {
		    ItemStack[] cloned = new ItemStack[stacks.length];
		    for (int i = 0; i < stacks.length; i++) {
		      if (stacks[i] != null)
		        cloned[i] = stacks[i].clone(); 
		    } 
		    return cloned;
		  }
	 public String ItemStackToString(ItemStack[] inventory)
	 {
		 return this.Itemstring(this.cloneStacks(inventory));
	 }
}
