package ml.lbplugins.oatsuaf;


import org.bukkit.Bukkit;

public class CheckTask implements Runnable {
   public void run() {
      try {
        Manager.spawnOat(Bukkit.getConsoleSender());
      } catch (Exception var10) {
         var10.printStackTrace();
      }

   }
}