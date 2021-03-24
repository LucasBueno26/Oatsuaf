package ml.lbplugins.oatsuaf;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin {
	
	public static Main instance;
	public static ArrayList<ItemStack> rando = new ArrayList<ItemStack>();
	
	@Override
	public void onEnable() {
		instance = this;
		registereventos();
	}

	
	public static Main getInstance() {
		return instance;
	}

	
	private void createItens() {
		//seleciona o que pode vim dentro do bau
		rando.add(new ItemStack(Material.GOLDEN_APPLE, 10, (short)1));
		rando.add(new ItemStack(Material.DIAMOND_BLOCK, 192));
		rando.add(new ItemStack(Material.DIAMOND_LEGGINGS));
	}
	
	public void registereventos() {
		System.out.print("§4[Oatsuaf]§e Carregando sistemas..");
		
		getServer().getPluginManager().registerEvents(new BarLife(), this);
		getServer().getPluginManager().registerEvents(new DeathOatsuaf(), this);
		getCommand("oatsuaf").setExecutor(new Comandos());
	    Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new CheckTask(), 0L, 20L*60*60*Main.getInstance().getConfig().getInt("Tempo"));
	    createItens();
		System.out.print("§4[Oatsuaf]§e Sistemas carregados.");
		
		if (!Main.getInstance().getConfig().contains("Oatsuaf.w")) {
			Bukkit.getConsoleSender().sendMessage("");
			System.out.print("§4[Oatsuaf]§e NECESSARIO DEFINIR AS LOCALIZACOES DE SPAWN!");
			Bukkit.getConsoleSender().sendMessage("");
			return;
		}
	}
}
