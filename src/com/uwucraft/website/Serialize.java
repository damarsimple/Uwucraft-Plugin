package com.uwucraft.website;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Serialize {


	public String Itemstring(ItemStack[] inventory)
	{
		String b = null;
		for (int i = 1; i < inventory.length; i++) {
			//Bukkit.getLogger().info("Item Lists " + this.inventory[i]);
			 b += inventory[i];
		}
		return b;
	}
	 public ItemStack[] cloneStacks(ItemStack[] stacks) {
		    ItemStack[] cloned = new ItemStack[stacks.length];
		    for (int i = 0; i < stacks.length; i++) {
		      if (stacks[i] != null)
		        cloned[i] = stacks[i].clone(); 
		    } 
		    return cloned;
		  }
	  public PotionEffect[] clonePotionEffects(PotionEffect[] effects) {
		    PotionEffect[] cloned = new PotionEffect[effects.length];
		    for (int i = 0; i < effects.length; i++) {
		      if (effects[i] != null)
		        cloned[i] = clonePotionEffect(effects[i]); 
		    } 
		    return cloned;
		  }
	  public static PotionEffect clonePotionEffect(PotionEffect effect) {
		    return new PotionEffect(effect.getType(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.hasParticles(), effect.hasIcon());
		  }
	 public String ItemStackToString(ItemStack[] inventory)
	 {
		 return this.Itemstring(this.cloneStacks(inventory)).replace("null", "").replace("ItemStack", "");
	 }
	 
}
