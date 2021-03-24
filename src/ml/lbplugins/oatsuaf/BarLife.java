package ml.lbplugins.oatsuaf;

import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BarLife implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void death(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof IronGolem) {
			IronGolem oatsuaf = (IronGolem) e.getEntity();
			Player p = (Player) e.getDamager();
			if (oatsuaf.getCustomName() != null) {
				if (oatsuaf.getCustomName().equalsIgnoreCase("§4Oatsuaf")) {
					// -> desuso -> p.getInventory().addItem(DeathOatsuaf.getRandomItem());

					// chance de aparecer a vida
					if (Math.random() < 0.02) {
						p.sendMessage("§eVocê recebeu uma dica: §fEle está com §c" + oatsuaf.getHealth() + "♥§f de vida.");
					}
					// chance de hitar
					if (Math.random() < 0.4) {
						p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 400, 1));
						oatsuaf.teleport(p);
					}
					if (Math.random() < 0.71) {
						e.setDamage(0);
					}
					if (Manager.hits.containsKey(p.getName())) {
						int qnt = Manager.hits.get(p.getName()) + 1;
						Manager.hits.put(p.getName(), qnt);
					} else {
						Manager.hits.put(p.getName(), 1);
					}
				}
			}
		}
	}

}
