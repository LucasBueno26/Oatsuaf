package ml.lbplugins.oatsuaf;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;

public class Manager {
	
	
	public static HashMap<String, Integer> hits = new HashMap<String, Integer>();
	
	public static Entity boss = null;
	
	public static int getBossHealth() {
		if (boss == null) {
			return 0;
		}
		return ((IronGolem) boss).getHealth();
	}

	public static void spawnOat(CommandSender sender) {

		// verifica se o usuario registrou a localização
		if (!Main.getInstance().getConfig().contains("Oatsuaf.w")) {
			sender.sendMessage("");
			sender.sendMessage("§4[Oatsuaf] §eO seu spawn nao foi definido.");
			sender.sendMessage("");
			return;
		}

		// pega a localização da config
		Double x = Main.getInstance().getConfig().getDouble("Oatsuaf.x");
		Double y = Main.getInstance().getConfig().getDouble("Oatsuaf.y");
		Double z = Main.getInstance().getConfig().getDouble("Oatsuaf.z");
		World world = Bukkit.getWorld(Main.getInstance().getConfig().getString("Oatsuaf.w"));

		// cria a localização
		Location loc = new Location(world, x, y, z);

		// verifica se já tem um oatsuaf
		for (Entity e : world.getEntities()) {
			if (e.getType().equals(EntityType.IRON_GOLEM)) {
				IronGolem oatsuaf = (IronGolem) e;
				if (oatsuaf.getCustomName() != null) {
					if (oatsuaf.getCustomName().equalsIgnoreCase("§4Oatsuaf")) {
						sender.sendMessage("");
						sender.sendMessage("§4[Oatsuaf] §eJa possui um oatsuaf vivo.");
						sender.sendMessage("");
						return;
					}
				}
			}
		}

		// verifica a chunk
		if (world.getChunkAt(loc).isLoaded() == false) {
			// carrega a chunk
			world.refreshChunk(loc.getChunk().getX(), loc.getChunk().getZ());
			sender.sendMessage("");
			sender.sendMessage("§4[Oatsuaf] §eA chunk teve que ser carregada!");
			sender.sendMessage("");
		}

		// spawn o oatsuaf
		IronGolem oat = (IronGolem) loc.getWorld().spawn(loc, IronGolem.class);
		boss = oat;
		oat.setCanPickupItems(false);
		oat.setCustomNameVisible(true);
		//9000 a vida original
		((IronGolem)boss).setMaxHealth(9000);
		((IronGolem)boss).setHealth((((IronGolem) boss).getMaxHealth()));
		oat.setCustomName("§4Oatsuaf");

		// anuncia o spawn do oatsuaf
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("§4[Oatsuaf]§f Quem sera capaz de me deter?");
		Bukkit.broadcastMessage("");
		hits.clear();

	}

}
