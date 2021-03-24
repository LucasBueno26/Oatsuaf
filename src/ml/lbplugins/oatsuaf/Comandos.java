package ml.lbplugins.oatsuaf;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;

public class Comandos implements CommandExecutor {

	public void checkExistens(String men, CommandSender s) {
		World world = Bukkit.getWorld(Main.getInstance().getConfig().getString("Oatsuaf.w"));
		for (Entity e : world.getEntities()) {
			if (e.getType().equals(EntityType.IRON_GOLEM)) {
				IronGolem oatsuaf = (IronGolem) e;
				if (oatsuaf.getCustomName() != null) {
					if (oatsuaf.getCustomName().equals("§4Oatsuaf")) {
						switch (men) {
						case "remove":
							oatsuaf.remove();
							s.sendMessage("§4[Oatsuaf]§f O boss foi removido.");
							break;
						case "life":
							int life = oatsuaf.getHealth();
							s.sendMessage("§4[Oatsuaf]§f Está com §c" + life + "§f de vida.");
							break;
						case "tp":
							Location loc = e.getLocation();
							Player p = (Player) s;
							p.teleport(loc);
							s.sendMessage("§4[Oatsuaf]§e Você foi teleportado até o boss.");
							break;
						}
					}

				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player || sender instanceof ConsoleCommandSender) {

			if (cmd.getName().equalsIgnoreCase("oatsuaf")) {
				if (!sender.hasPermission("oatsuaf.admin")) {
					return true;
				}
				if (args.length == 0) {
					sender.sendMessage("§c/oatsuaf spawn §e- §fSpawna o boss");
					sender.sendMessage("§c/oatsuaf set §e- §fSete a localização do boss");
					sender.sendMessage("§c/oatsuaf tp §e- §fTeletransporte ao boss");
					sender.sendMessage("§c/oatsuaf life §e- §fConfira a vida do boss");
					sender.sendMessage("§c/oatsuaf remove §e- §fRemova o boss");
					return true;
				}
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("spawn")) {
						Manager.spawnOat(sender);
					}
					if (args[0].equalsIgnoreCase("remove")) {
						checkExistens("remove", sender);
					}
					if (args[0].equalsIgnoreCase("life")) {
						checkExistens("life", sender);
					}
					if (args[0].equalsIgnoreCase("tp")) {
						checkExistens("tp", sender);
					}
					if (args[0].equalsIgnoreCase("set")) {
						Player p = (Player) sender;

						// consulta as localizações
						Double x = p.getLocation().getX();
						Double y = p.getLocation().getY();
						Double z = p.getLocation().getZ();
						World w = p.getLocation().getWorld();

						// salva todas as localizações
						Main.getInstance().getConfig().set("Oatsuaf.w", w.getName());
						Main.getInstance().getConfig().set("Oatsuaf.x", x);
						Main.getInstance().getConfig().set("Oatsuaf.y", y);
						Main.getInstance().getConfig().set("Oatsuaf.z", z);
						Main.getInstance().saveConfig();

						p.sendMessage("§4[Oatsuaf]§e A localização do spawn foi salva.");

						return true;
					}
				}
			}
		}
		return false;
	}

}
