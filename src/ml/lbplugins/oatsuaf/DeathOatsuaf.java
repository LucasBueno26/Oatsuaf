package ml.lbplugins.oatsuaf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;



public class DeathOatsuaf implements Listener {

	// public static ItemStack[] possibleItems = {new ItemStack(Material.DIAMOND,
	// 12), new ItemStack(Material.EMERALD), new ItemStack(Material.IRON_INGOT, 32)
	// };

	// public static ItemStack getRandomItem() {
	// Random rand = new Random();
	// return possibleItems[rand.nextInt(possibleItems.length)];
	// }

	
	@EventHandler
	public void onchat(ChatMessageEvent e) {
		Player p = (Player) e.getSender();
		if (Main.getInstance().getConfig().getString("Matador").equalsIgnoreCase(p.getName())) {
			e.setTagValue("oatsuaf"," §c[Boss]");
		}
	}
	
	@EventHandler
	public void onEntity(EntityDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			if (e.getEntity().getCustomName() != null) {
				if (e.getEntity().getCustomName().equals("§4Oatsuaf")) {

					// consulta o jogador que matou
					Player ganhador = e.getEntity().getKiller();
					Bukkit.broadcastMessage("§d[Server] " + ganhador.getName() + " matou o oatsuaf.");

					// entraga de fragmentos vip

					// entre a tag e salva na config o usuario
					Main.getInstance().getConfig().set("Matador", ganhador.getName());
					Main.getInstance().saveConfig();

					// spawna o bau com as recompensas
					Location loc = e.getEntity().getLocation();
					loc.getBlock().setType(Material.CHEST);
					Chest bau = (Chest) loc.getBlock().getState();
					new BukkitRunnable() {

						@Override
						public void run() {
							loc.getBlock().setType(Material.AIR);
						}
					}.runTaskLater(Main.getInstance(), 20L * 60);

					
					//sortea o item a entregar no bau
					Random r = new Random();
					ItemStack premio = Main.rando.get(r.nextInt(Main.rando.size()));
					//verifica se o item é uma parte p4
				
					if (premio.getType().equals(Material.DIAMOND_LEGGINGS)) {
						ItemStack[] p4s = { new ItemStack(Material.DIAMOND_LEGGINGS),
								new ItemStack(Material.DIAMOND_BOOTS), new ItemStack(Material.DIAMOND_CHESTPLATE),
								new ItemStack(Material.DIAMOND_HELMET) };
						//envia todo o p4 full para o bau
						for (ItemStack p4 : p4s) {
							p4.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
							p4.addEnchantment(Enchantment.DURABILITY, 3);
							bau.getInventory().addItem(p4);
						}
					} else {
						bau.getInventory().addItem(premio);
					}

					//notifica a staff sobre o item que foi enviado ao bau
					for (ItemStack i : bau.getInventory().getContents()) {
						for (Player stf : Bukkit.getOnlinePlayers()) {
							if (!stf.hasPermission("oatsuaf.admin")) {
								return;
							}
							if (i != null) {
								stf.sendMessage("§cItem Oatsuaf:§f " + i.toString());
							}
						}
					}
					if (Math.random() < 0.2) {
						List<String> rank = new ArrayList<String>(Manager.hits.keySet());
						Collections.sort(rank, new Comparator<String>() {
							@Override
							public int compare(String s1, String s2) {
								Integer popularity1 = Manager.hits.get(s1);
								Integer popularity2 = Manager.hits.get(s2);
								return popularity2.compareTo(popularity1);
							}
						});
						int i = 0;
						for (String s : rank) {
							i++;
							if (i == 1)
								// avisa o servidor
								Bukkit.broadcastMessage(
										"§4" + s + "§f foi quem mais deu hit e ganhou 1 §6estrela do nether§f.");
							Player novo = Bukkit.getPlayer(s);
							// recompensa o jogador
							novo.getInventory().addItem(new ItemStack(Material.NETHER_STAR));
							break;
						}
					}
				}
			}
		}

	}
}
